package com.dmitry.shnurenko.spring.mvc.controllers.register;

import javax.annotation.Nonnull;

/**
 * The class contains name of field from form in view and wraps them via enum.
 *
 * @author Dmitry Shnurenko
 */
public enum RegisterFormNames {
    LOGIN("login"), EMAIL("email"), PASSWORD("password"), REPEAT_PASSWORD("repeatPassword");

    private final String fieldName;

    RegisterFormNames(@Nonnull String fieldName) {
        this.fieldName = fieldName;
    }

    /** Returns name which is wrote in form on view. */
    @Nonnull
    @Override
    public String toString() {
        return fieldName;
    }
}
