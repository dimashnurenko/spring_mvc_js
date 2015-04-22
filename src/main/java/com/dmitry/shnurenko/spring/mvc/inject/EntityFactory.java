package com.dmitry.shnurenko.spring.mvc.inject;

import com.dmitry.shnurenko.spring.mvc.entity.access.User;
import com.dmitry.shnurenko.spring.mvc.entity.employees.Employee;
import com.dmitry.shnurenko.spring.mvc.entity.employees.Manager;
import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Factory which allows create entity of employees.
 *
 * @author Dmitry Shnurenko
 */
@Component("entityFactory")
public class EntityFactory {

    /**
     * Creates entity of manager using inputting parameters.
     *
     * @param id        id which need set to manager
     * @param firstName first name which need set to manager
     * @param lastName  last name  which need set to manager
     * @return an instance of {@link Manager}
     */
    @Nonnull
    public Employee createManager(@Nonnegative int id, @Nonnull String firstName, @Nonnull String lastName) {
        return new Manager(id, firstName, lastName);
    }

    /**
     * Creates entity of user using inputting parameters.
     *
     * @param login    login which need set to user
     * @param email    email which need set to user
     * @param password password  which need set to user
     * @return an instance of {@link Manager}
     */
    @Nonnull
    public User createUser(@Nonnull String login, @Nonnull String email, @Nonnull String password) {
        return new User(login, email, password);
    }

    /**
     * Creates entity of address
     *
     * @param country country which need set
     * @param city    city which need set
     * @param street  street which need set
     * @param house   house which need set
     * @param flat    flat which need set
     * @return an instance of {@link Address}
     */
    @Nonnull
    public Address createAddress(@Nonnull String country,
                                 @Nonnull String city,
                                 @Nullable String street,
                                 @Nonnegative int house,
                                 @Nonnegative int flat) {
        return new Address(country, city, street, house, flat);
    }

    /**
     * Creates empty simple object with default fields values.
     *
     * @return an instance of {@link Address}
     */
    @Nonnull
    public Address createAddress() {
        return new Address();
    }
}
