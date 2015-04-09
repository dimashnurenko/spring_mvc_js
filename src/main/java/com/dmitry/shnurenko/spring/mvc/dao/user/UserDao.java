package com.dmitry.shnurenko.spring.mvc.dao.user;

import com.dmitry.shnurenko.spring.mvc.entity.access.User;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;

import javax.annotation.Nonnull;

/**
 * Provides methods to save users to data base.
 *
 * @author Dmitry Shnurenko
 */
public interface UserDao {

    /**
     * Saves user to data base.The method can throw {@link DBException}
     *
     * @param user user which need to save
     */
    void save(@Nonnull User user) throws DBException;

    /**
     * The method checks if data base contains user with current login and password.
     *
     * @param login    login of user
     * @param password password of user
     * @return <code>true</code> data base contains user,<code>false</code> data base doesn't contains user
     */
    boolean isUserLogin(@Nonnull String login, @Nonnull String password) throws DBException;
}
