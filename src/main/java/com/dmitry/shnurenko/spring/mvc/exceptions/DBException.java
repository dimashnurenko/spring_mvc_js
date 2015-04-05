package com.dmitry.shnurenko.spring.mvc.exceptions;

import javax.annotation.Nonnull;

/**
 * This exceptions are thrown when we get error related to work with data base.
 *
 * @author Dmitry Shnurenko
 */
public class DBException extends Exception {

    private final String    message;
    private final Throwable throwable;

    public DBException(@Nonnull Throwable throwable, @Nonnull String message) {
        super(throwable.getCause());

        this.message = message;
        this.throwable = throwable;
    }

    @Override
    @Nonnull
    public String getMessage() {
        return message;
    }

    @Nonnull
    public Throwable getThrowable() {
        return throwable;
    }
}
