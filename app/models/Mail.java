package models;

import play.Logger;
import plugins.MailerPlugin;

import javax.mail.*;
import javax.mail.internet.*;

import static javax.mail.Message.RecipientType.TO;

public class Mail {

    private final String subject;
    private final String htmlBody;
    private final String textBody;
    private final String to;

    public Mail(String to, String subject, String htmlBody, String textBody) {
        this.to = to;
        this.subject = subject;
        this.htmlBody = htmlBody;
        this.textBody = textBody;
    }

    public void send() {
        if(!MailerPlugin.isEnabled()) {
            Logger.debug(htmlBody);
            return;
        }
        try {
            Session mailSession = Session.getDefaultInstance(MailerPlugin.properties, MailerPlugin.authenticator);
            //mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);

            Multipart multipart = new MimeMultipart("alternative");

            BodyPart part1 = new MimeBodyPart();
            part1.setText(textBody);

            BodyPart part2 = new MimeBodyPart();
            part2.setContent(htmlBody, "text/html");

            multipart.addBodyPart(part1);
            multipart.addBodyPart(part2);

            message.setContent(multipart);
            message.setFrom(new InternetAddress("me@myhost.com"));
            message.setSubject(subject);
            message.addRecipient(TO, new InternetAddress(to));

            transport.connect();
            transport.sendMessage(message, message.getRecipients(TO));
            transport.close();
        } catch (NoSuchProviderException e) {
            throw new IllegalStateException(e);
        } catch (AddressException e) {
            throw new IllegalStateException(e);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }

}
