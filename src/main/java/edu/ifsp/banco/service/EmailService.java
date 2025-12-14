package edu.ifsp.banco.service;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    private static final String MEU_EMAIL = "sr.freeza@gmail.com"; 
    private static final String MINHA_SENHA_APP = "zctv qtlg syfx gzcb"; // senha de apps gerada na config de conta
//    Passo a Passo para criar senha de app (Google Account)
//    Acesse myaccount.google.com/security.
//    Ative a Verificação em duas etapas (2-Step Verification) se ainda não estiver ativa. Sem isso, a opção de senha de app não aparece.
//    Na barra de busca no topo da página, digite: "Senhas de app" (ou "App passwords").
//    Clique na opção que aparecer.
//    Dê um nome (ex: "BitPay") e clique em Criar.
//    O Google vai gerar uma senha de 16 caracteres aleatórios (ex: abcd efgh ijkl mnop).
//    Copie essa senha no campo MINHA_SENHA_APP.

    public void enviarEmailRecuperacao(String emailDestino, String token) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MEU_EMAIL, MINHA_SENHA_APP);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MEU_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            message.setSubject("BitPay - Recuperação de Senha");

            String link = "http://localhost:8080/seu-projeto/app?command=validarToken&token=" + token;
            
            String htmlContent = "<h3>Recuperação de Senha</h3>"
                    + "<p>Clique no link abaixo para redefinir sua senha:</p>"
                    + "<a href='" + link + "'>Redefinir Senha</a>"
                    + "<br><br><small>Link válido por 30 minutos.</small>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email enviado para: " + emailDestino);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}