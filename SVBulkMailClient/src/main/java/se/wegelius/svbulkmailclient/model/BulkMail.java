/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmailclient.model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author asawe
 */
public class BulkMail {
    private String smtp;
    private String port;
    private String from;

    public BulkMail() {
        this.smtp = "send.one.com";
        this.port = "465";
        this.from = "asa@wegelius.se";
    }

    public BulkMail(String smtp, String port, String from) {
        this.smtp = smtp;
        this.port = port;
        this.from = from;
    }
    
    
    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSmtp() {
        return smtp;
    }

    public String getPort() {
        return port;
    }

    public String getFrom() {
        return from;
    }

    public void sendMailSSL(String msg, String receiver, String subject, final String password){   
		Properties props = new Properties();
		props.put("mail.smtp.host", smtp);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
        		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiver));
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    
    public void sendBulk(String[] receivers, String msg, String subject, String password){
        for(String to: receivers){
            sendMailSSL(msg, to, subject, password);
        }
    }
}
