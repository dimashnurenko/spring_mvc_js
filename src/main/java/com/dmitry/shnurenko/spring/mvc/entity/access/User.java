package com.dmitry.shnurenko.spring.mvc.entity.access;

import javax.annotation.Nonnull;

/**
 * Contains information about user who have access to employees database.
 *
 * @author Dmitry Shnurenko
 */
public class User {

    private final String login;
    private final String email;
    private final String password;

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    @Nonnull
    public String getLogin() {
        return login;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }
}
