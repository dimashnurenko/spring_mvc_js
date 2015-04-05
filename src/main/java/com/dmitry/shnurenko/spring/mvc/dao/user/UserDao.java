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
}
