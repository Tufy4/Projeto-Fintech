/* =========================================================
   LIMPEZA
   ========================================================= */
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE PARCELAS_EMPRESTIMO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE EMPRESTIMOS CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE CONFIG_EMPRESTIMO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE INVESTIMENTOS CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE MOVIMENTACOES CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE CONTAS CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE USUARIOS CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE TOKENS_RECUPERACAO CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL; END;
/

-- Drops de Sequences
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_USUARIOS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_CONTAS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_MOVIMENTACOES'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_INVESTIMENTOS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_EMPRESTIMOS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_PARCELAS_EMPRESTIMO'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_NUM_CONTA'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_CONFIG_EMPRESTIMO'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_TOKENS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

/* =========================================================
   TABELAS E SEQUENCES
   ========================================================= */

CREATE SEQUENCE seq_usuarios START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_contas START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_movimentacoes START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_investimentos START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_config_emprestimo START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_emprestimos START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_parcelas_emprestimo START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_num_conta START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_tokens START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE usuarios (
    id                          INT NOT NULL PRIMARY KEY,
    nome                        VARCHAR2(255) NOT NULL,
    email                       VARCHAR2(255) UNIQUE NOT NULL,
    senha                       VARCHAR2(255) NOT NULL,
    telefone                    VARCHAR2(20),
    endereco                    VARCHAR(50),
    status                      VARCHAR2(20) DEFAULT 'BLOQUEADO' CHECK ( status IN ( 'ATIVO', 'INATIVO', 'BLOQUEADO' ) ),
    data_criacao                TIMESTAMP DEFAULT current_timestamp,
    data_ultima_atualizacao     TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE contas (
    id           INT NOT NULL PRIMARY KEY,
    agencia      INT DEFAULT 5 NOT NULL,
    numero_conta INT NOT NULL,
    usuario_id   INT NOT NULL,
    saldo        NUMBER(15, 2) DEFAULT 0.00,
    tipo         VARCHAR2(20) DEFAULT 'CLIENTE' CHECK ( tipo IN ( 'CLIENTE', 'GERENTE' ) ),
    data_criacao TIMESTAMP DEFAULT current_timestamp,
    CONSTRAINT fk_contas_usuarios FOREIGN KEY ( usuario_id ) REFERENCES usuarios ( id )
);

CREATE TABLE movimentacoes (
    id               INT NOT NULL PRIMARY KEY,
    conta_origem_id  INT,
    conta_destino_id INT,
    valor            NUMBER(15, 2) NOT NULL,
    saldo_anterior   NUMBER(15, 2) NOT NULL,
    saldo_posterior  NUMBER(15, 2) NOT NULL,
    tipo             VARCHAR2(50) CHECK ( tipo IN ( 'DEPOSITO', 'SAQUE', 'INVESTIMENTO', 'EMPRESTIMO', 'TRANSFERENCIA_ENVIADA',
                                        'TRANSFERENCIA_RECEBIDA', 'PAGAMENTO' ) ),
    data_transacao   TIMESTAMP DEFAULT current_timestamp,
    descricao        VARCHAR(200),
    status           VARCHAR2(20) DEFAULT 'PENDENTE' CHECK ( status IN ( 'PENDENTE', 'CONCLUIDA', 'FALHA' ) ),
    CONSTRAINT fk_mov_conta_origem FOREIGN KEY ( conta_origem_id )
        REFERENCES contas ( id ),
    CONSTRAINT fk_mov_conta_destino FOREIGN KEY ( conta_destino_id )
        REFERENCES contas ( id )
);

CREATE TABLE investimentos (
    id                INT NOT NULL PRIMARY KEY,
    conta_id          INT NOT NULL,
    tipo_investimento VARCHAR2(100) NOT NULL,
    valor_investido   NUMBER(15, 2) NOT NULL,
    data_inicio       DATE DEFAULT sysdate,
    data_fim          DATE,
    status            VARCHAR2(20) DEFAULT 'ATIVO' CHECK ( status IN ( 'ATIVO', 'ENCERRADO' ) ),
    CONSTRAINT fk_invest_conta FOREIGN KEY ( conta_id ) REFERENCES contas ( id )
);

CREATE TABLE config_emprestimo (
    id                INT PRIMARY KEY,
    taxa_juros_padrao NUMBER(5, 2) NOT NULL
);

CREATE TABLE emprestimos (
    id                    INT NOT NULL PRIMARY KEY,
    conta_id              INT NOT NULL,
    valor_emprestimo      NUMBER(10, 2) NOT NULL,
    taxa_juros_mensal     NUMBER(5, 2) NOT NULL,
    parcelas              INT NOT NULL,
    status                VARCHAR2(30) DEFAULT 'SIMULADO' CHECK ( status IN ( 'SIMULADO', 'SOLICITADO', 'APROVADO', 'REJEITADO', 'EM_ANDAMENTO', 'QUITADO' ) ),
    data_solicitacao      DATE DEFAULT sysdate,
    data_aprovacao        DATE,
    data_ultimo_pagamento DATE,
    CONSTRAINT fk_emprestimo_conta FOREIGN KEY ( conta_id ) REFERENCES contas ( id ),
    CONSTRAINT uk_emprestimo_duplicado UNIQUE ( conta_id, valor_emprestimo, parcelas, taxa_juros_mensal )
);

CREATE TABLE parcelas_emprestimo (
    id                INT NOT NULL PRIMARY KEY,
    emprestimo_id     INT NOT NULL,
    numero_parcela    INT NOT NULL,
    valor_amortizacao NUMBER(15, 2),
    valor_juros       NUMBER(15, 2),
    valor_parcela     NUMBER(15, 2) NOT NULL,
    data_vencimento   DATE NOT NULL,
    data_pagamento    DATE,
    status            VARCHAR2(20) DEFAULT 'PENDENTE' CHECK ( status IN ( 'PENDENTE', 'PAGO', 'ATRASADO' ) ),
    CONSTRAINT fk_parcelas_emprestimo FOREIGN KEY ( emprestimo_id ) REFERENCES emprestimos ( id )
);

CREATE TABLE tokens_recuperacao (
    id           INT PRIMARY KEY,
    usuario_id   INT NOT NULL,
    token        VARCHAR2(64) NOT NULL UNIQUE,
    data_expiracao TIMESTAMP NOT NULL,
    utilizado    NUMBER(1) DEFAULT 0, -- 0=Não, 1=Sim
    CONSTRAINT fk_token_user FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);


/* =========================================================
   MOCKS
   ========================================================= */

-- 1. Configuração Base da taxa de juros do Empréstimo
INSERT INTO config_emprestimo (id, taxa_juros_padrao) VALUES (seq_config_emprestimo.NEXTVAL, 1.99);


-- 2. USUÁRIO MIGUEL (GERENTE)
INSERT INTO usuarios (id, nome, email, senha, telefone, endereco, status) 
VALUES (seq_usuarios.NEXTVAL, 'Miguel Batista', '123', '1', '(11) 98765-4321', 'Rua Gerente, 123', 'ATIVO');

INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (
    seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL, 
    (SELECT id FROM usuarios WHERE email = '123'), 
    5000.00, 'GERENTE'
);

INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao)
VALUES (
    seq_movimentacoes.NEXTVAL,
    (SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = '123')),
    5000.00,
    0.00,
    5000.00,
    'DEPOSITO',
    'CONCLUIDA',
    'Deposito Inicial de Abertura'
);


-- 3. USUÁRIO ANA (CLIENTE)
INSERT INTO usuarios (id, nome, email, senha, telefone, endereco, status)
VALUES (seq_usuarios.NEXTVAL, 'Ana Silva', 'ana', '1', '(11) 90000-0000', 'Rua Cliente, 10', 'ATIVO');

INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (
    seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL,
    (SELECT id FROM usuarios WHERE email = 'ana'),
    1000.00, 'CLIENTE'
);

INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao)
VALUES (
    seq_movimentacoes.NEXTVAL,
    (SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'ana')),
    1000.00,
    0.00,
    1000.00,
    'DEPOSITO',
    'CONCLUIDA',
    'Deposito Inicial de Abertura'
);

COMMIT;