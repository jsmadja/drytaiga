package models;

import plugins.MailerPlugin;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

    private String to;

    public Mail(String to) {
        this.to = to;
    }

    public void send() throws Exception {
        Session mailSession = Session.getDefaultInstance(MailerPlugin.properties, MailerPlugin.authenticator);
        //mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);

        Multipart multipart = new MimeMultipart("alternative");

        BodyPart part1 = new MimeBodyPart();
        part1.setText("This is multipart mail and u read part1......");

        BodyPart part2 = new MimeBodyPart();
        part2.setContent("<b>This is multipart mail and u read part2......</b>", "text/html");

        multipart.addBodyPart(part1);
        multipart.addBodyPart(part2);

        message.setContent(multipart);
        message.setFrom(new InternetAddress("me@myhost.com"));
        message.setSubject("This is the subject");
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));

        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

}
