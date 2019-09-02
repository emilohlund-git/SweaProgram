package com.mycompany.dbprogrammet;

import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class Epost {

    //Loggare
    Loggare loggare;

    //Avsändare och mottagare
    String to = "help@emilohlund.se";
    String from = "programmet@emilohlund.se";

    //Användarnamn och lösenord för one.com
    final String username = "programmet@emilohlund.se";
    final String password = "********";

    //Skickas via
    String host = "send.one.com";
    String port = "465";

    public void skicka_loggar(String fil, String fel) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.socketFactory.port", port);
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.host", host);
        prop.setProperty("mail.smtp.port", port);
        prop.put("mail.smtp.quitwait", "false");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPassWordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("Loggar från programmet");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Loggar");
            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String filename = fil;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fel);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect(host, username, password);
            t.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException mex) {
            loggare.log_info("", "Epost_skicka_loggar", mex);
        }
    }

    public void rapporteraBug(String problemet, String händelse, String tidpunkt, 
                              File mainFrame, File orderFrame, File offertFrame,
                              boolean orderVisible, boolean offertVisible) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.socketFactory.port", port);
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.host", host);
        prop.setProperty("mail.smtp.port", port);
        prop.put("mail.smtp.quitwait", "false");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPassWordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("Bug Rapportering");

            MimeMultipart multiPart = new MimeMultipart("Related");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            MimeBodyPart mainFrameBodyPart = new MimeBodyPart();
            MimeBodyPart orderFrameBodyPart = new MimeBodyPart();
            MimeBodyPart offertFrameBodyPart = new MimeBodyPart();

            String msg = 
            "<b>Användare upplever problem med:</b><br>" +
            problemet + "<br><br>" +
            "<b>Beskrivning av problemet:</b><br>" + 
            händelse + "<br><br>" + 
            "<b>Problemet uppstod ungefär:</b><br>" +
            tidpunkt;        
            
            messageBodyPart.setContent(msg, "text/html");
            mainFrameBodyPart.attachFile(mainFrame);
            mainFrameBodyPart.setHeader("Content-ID", "<image>");
            multiPart.addBodyPart(messageBodyPart);
            
            if(orderVisible) {
                orderFrameBodyPart.attachFile(orderFrame);
                multiPart.addBodyPart(orderFrameBodyPart);
            }
            if(offertVisible) {
                offertFrameBodyPart.attachFile(offertFrame);
                multiPart.addBodyPart(offertFrameBodyPart);
            }
            
            multiPart.addBodyPart(mainFrameBodyPart);
            message.setContent(multiPart);
            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect(host, username, password);
            t.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException mex) {
            loggare.log_info("", "Epost_skicka_epost", mex);
        } catch (IOException ex) {
            Logger.getLogger(Epost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void skicka_lösenord (String lösenord, String mail) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.socketFactory.port", port);
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.host", host);
        prop.setProperty("mail.smtp.port", port);
        prop.put("mail.smtp.quitwait", "false");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPassWordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(mail));

            message.setSubject("Lösenordet");
            message.setText("Lösenordet till inställningarna är: " + lösenord);

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect(host, username, password);
            t.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException mex) {
            loggare.log_info("", "Epost_skicka_epost", mex);
        }
    }
    
    public void skickaLösenordPåminnelse (String mail, String lösen) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.socketFactory.port", port);
        prop.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.host", host);
        prop.setProperty("mail.smtp.port", port);
        prop.put("mail.smtp.quitwait", "false");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPassWordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(mail));

            message.setSubject("Lösenordet");
            message.setText("Ditt lösenord är: " + lösen);

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect(host, username, password);
            t.sendMessage(message, message.getAllRecipients());

        } catch (MessagingException mex) {
            loggare.log_info("", "Epost_skicka_epost", mex);
        }
    }

}
