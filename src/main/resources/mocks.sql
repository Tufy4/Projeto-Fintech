/* =========================================================
   MOCKS
   ========================================================= */

-- 1 CONFIGURAÇÃO
INSERT INTO config_emprestimo (id, taxa_juros_padrao) 
VALUES (seq_config_emprestimo.NEXTVAL, 1.99);

-- 2 USUÁRIOS
INSERT INTO usuarios (id, nome, email, senha, telefone, endereco, status) 
VALUES (seq_usuarios.NEXTVAL, 'Miguel Batista', '123', '1', '(11) 98765-4321', 'Rua Matriz, 100', 'ATIVO');

INSERT INTO usuarios (id, nome, email, senha, telefone, endereco, status)
VALUES (seq_usuarios.NEXTVAL, 'Ana Silva', 'ana', '1', '(11) 91111-1111', 'Rua das Flores, 10', 'ATIVO');

INSERT INTO usuarios (id, nome, email, senha, telefone, endereco, status)
VALUES (seq_usuarios.NEXTVAL, 'Bob Costa', 'bob', '1', '(11) 92222-2222', 'Av. Paulista, 500', 'ATIVO');

INSERT INTO usuarios (id, nome, email, senha, telefone, endereco, status)
VALUES (seq_usuarios.NEXTVAL, 'Carol Dias', 'carol', '1', '(21) 93333-3333', 'Rua da Praia, 20', 'ATIVO');


-- 3 CONTAS
-- Miguel (Gerente e Pessoal)
INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL, (SELECT id FROM usuarios WHERE email = '123'), 4500.00, 'GERENTE');

INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL, (SELECT id FROM usuarios WHERE email = '123'), 1500.00, 'CLIENTE');

-- Ana
INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL, (SELECT id FROM usuarios WHERE email = 'ana'), 900.00, 'CLIENTE');

-- Bob (Principal e Reserva)
INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL, (SELECT id FROM usuarios WHERE email = 'bob'), 1600.00, 'CLIENTE');

INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL, (SELECT id FROM usuarios WHERE email = 'bob'), 500.00, 'CLIENTE');

-- Carol
INSERT INTO contas (id, agencia, numero_conta, usuario_id, saldo, tipo)
VALUES (seq_contas.NEXTVAL, 0001, seq_num_conta.NEXTVAL, (SELECT id FROM usuarios WHERE email = 'carol'), 1000.00, 'CLIENTE');


-- 4 MOVIMENTAÇÕES
-- Depósitos Iniciais
INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL, 
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = '123') AND tipo = 'GERENTE'),
5000.00, 0.00, 5000.00, 'DEPOSITO', 'CONCLUIDA', 'Bonus Chefia', sysdate - 10);

INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL, 
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = '123') AND tipo = 'CLIENTE'),
1000.00, 0.00, 1000.00, 'DEPOSITO', 'CONCLUIDA', 'Deposito Pessoal', sysdate - 10);

INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL, 
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'ana')),
1000.00, 0.00, 1000.00, 'DEPOSITO', 'CONCLUIDA', 'Salário', sysdate - 10);

INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL, 
(SELECT MIN(id) FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'bob')),
2000.00, 0.00, 2000.00, 'DEPOSITO', 'CONCLUIDA', 'Venda de Carro', sysdate - 10);

INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL, 
(SELECT MAX(id) FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'bob')),
500.00, 0.00, 500.00, 'DEPOSITO', 'CONCLUIDA', 'Economias', sysdate - 10);

INSERT INTO movimentacoes (id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL, 
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'carol')),
500.00, 0.00, 500.00, 'DEPOSITO', 'CONCLUIDA', 'Mesada', sysdate - 10);


-- Transferência: Miguel (Gerente) -> Miguel (Pessoal)
INSERT INTO movimentacoes (id, conta_origem_id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL,
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = '123') AND tipo = 'GERENTE'),
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = '123') AND tipo = 'CLIENTE'),
500.00, 5000.00, 4500.00, 'TRANSFERENCIA_ENVIADA', 'CONCLUIDA', 'Para minha conta pessoal', sysdate - 5);

INSERT INTO movimentacoes (id, conta_origem_id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL,
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = '123') AND tipo = 'GERENTE'),
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = '123') AND tipo = 'CLIENTE'),
500.00, 1000.00, 1500.00, 'TRANSFERENCIA_RECEBIDA', 'CONCLUIDA', 'Recebido da conta Gerente', sysdate - 5);


-- Transferência: Ana -> Bob (Conta 1)
INSERT INTO movimentacoes (id, conta_origem_id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL,
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'ana')),
(SELECT MIN(id) FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'bob')),
100.00, 1000.00, 900.00, 'TRANSFERENCIA_ENVIADA', 'CONCLUIDA', 'Pagamento serviço', sysdate - 3);

INSERT INTO movimentacoes (id, conta_origem_id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL,
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'ana')),
(SELECT MIN(id) FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'bob')),
100.00, 2000.00, 2100.00, 'TRANSFERENCIA_RECEBIDA', 'CONCLUIDA', 'Recebido de Ana', sysdate - 3);


-- Transferência: Bob (Conta 1) -> Carol
INSERT INTO movimentacoes (id, conta_origem_id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL,
(SELECT MIN(id) FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'bob')),
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'carol')),
500.00, 2100.00, 1600.00, 'TRANSFERENCIA_ENVIADA', 'CONCLUIDA', 'Presente Aniversário', sysdate - 1);

INSERT INTO movimentacoes (id, conta_origem_id, conta_destino_id, valor, saldo_anterior, saldo_posterior, tipo, status, descricao, data_transacao)
VALUES (seq_movimentacoes.NEXTVAL,
(SELECT MIN(id) FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'bob')),
(SELECT id FROM contas WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'carol')),
500.00, 500.00, 1000.00, 'TRANSFERENCIA_RECEBIDA', 'CONCLUIDA', 'Presente do Bob', sysdate - 1);

COMMIT;