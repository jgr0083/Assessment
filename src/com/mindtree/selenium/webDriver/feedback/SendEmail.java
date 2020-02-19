package com.mindtree.selenium.webDriver.feedback;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	public void send(String testTitle) {
		

		String to = "ieatformyfriends@gmail.com";
		String from ="jarellr@gmail.com";
		
		String host = "smtp.gmail.com";
		
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(to, "Evilyoshi21313!");

            }

        });
		
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(testTitle);

            // Now set the actual message
            message.setText(testTitle + " has been completed");

            //Body of message
            BodyPart messageBodyPart = new MimeBodyPart();
            
            messageBodyPart.setText("Logs for "+testTitle+ " are attached");
            
            Multipart multipart = new MimeMultipart();
            
            multipart.addBodyPart(messageBodyPart);
            
            //Attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "..\\Assessment\\log\\logfile.log";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            
            message.setContent(multipart);
            
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
	}
	
}
