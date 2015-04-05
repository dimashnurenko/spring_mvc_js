package com.dmitry.shnurenko.spring.mvc.util.sendemail;

import javax.annotation.Nonnull;

/**
 * The builder provides methods which allows set up send mail to possible email address and add to email some
 * subject, text or html content. The method send must be called when sender finally set up.
 *
 * @author Dmitry Shnurenko
 */
public interface MailSenderBuilder {

    /**
     * Add address on which email will be sent and returns partly set up sender.
     *
     * @param email address on which need to send message
     * @return instance of {@link MailSenderBuilder}
     */
    @Nonnull MailSenderBuilder withAddress(@Nonnull String email);

    /**
     * Add subject to email and returns partly set up sender.
     *
     * @param subject subject which need set to message
     * @return instance of {@link MailSenderBuilder}
     */
    @Nonnull MailSenderBuilder withSubject(@Nonnull String subject);

    /**
     * Add string content of message and returns partly set up sender.
     *
     * @param text content which need set to message
     * @return instance of {@link MailSenderBuilder}
     */
    @Nonnull MailSenderBuilder withText(@Nonnull String text);

    /**
     * Add реьд content of message and returns partly set up sender.
     *
     * @param html content which need set to message
     * @return instance of {@link MailSenderBuilder}
     */
    @Nonnull MailSenderBuilder withHtml(@Nonnull String html);

    /** Sends message to recipient via special mail server. */
    void send();
}
