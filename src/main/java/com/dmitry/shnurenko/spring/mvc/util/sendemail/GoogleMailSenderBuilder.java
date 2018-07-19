package com.dmitry.shnurenko.spring.mvc.util.sendemail;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Contains methods which set up google mail sender and send message.
 *
 * @author Dmitry Shnurenko
 */
@Component("googleMailSenderBuilder")
public class GoogleMailSenderBuilder implements MailSenderBuilder {

    private static final String FROM     = "";
    private static final String PASSWORD = "";
    private static final String HOST     = "smtp.gmail.com";

    private final MimeMessage message;
    private final Session     session;

    public GoogleMailSenderBuilder() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", HOST);
        props.put("mail.smtp.user", FROM);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(props);
        message = new MimeMessage(session);
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public MailSenderBuilder withAddress(@Nonnull String email) {
        try {
            InternetAddress address = new InternetAddress(email);
            message.addRecipient(Message.RecipientType.TO, address);
        } catch (MessagingException e) {
            throw new IllegalStateException("Can't get email address..." + e.getCause());
        }

        return this;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public MailSenderBuilder withSubject(@Nonnull String subject) {
        try {
            message.setSubject(subject);
        } catch (MessagingException e) {
            throw new IllegalStateException("Can't apply subject" + e.getCause());
        }

        return this;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public MailSenderBuilder withText(@Nonnull String text) {
        try {
            message.setText(text);
        } catch (MessagingException e) {
            throw new IllegalStateException("Can't set text to email" + e.getCause());
        }

        return this;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public MailSenderBuilder withHtml(@Nonnull String html) {
        try {
            message.setContent(html, "text/html");
        } catch (MessagingException e) {
            throw new IllegalStateException("Can't set html to email" + e.getCause());
        }

        return this;
    }

    /** {inheritDoc} */
    @Override
    public void send() {
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, FROM, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new IllegalStateException("Can't send email" + e.getCause());
        }

    }

}
