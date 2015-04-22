package com.dmitry.shnurenko.spring.mvc.entity.employees;

import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * Provides methods which allows change or get information about employee.
 *
 * @author Dmitry Shnurenko
 */
public interface Employee extends Serializable {

    /**
     * Gets id of current employee. This id is unique for every employee.
     *
     * @return an instance of id
     */
    @Nonnegative int getId();

    /**
     * Sets id of employee.
     *
     * @param id id which need set
     */
    void setId(@Nonnegative int id);

    /** @return value of first name */
    @Nonnull String getFirstName();

    /**
     * Sets first name of employee.
     *
     * @param firstName name which need set
     */
    void setFirstName(@Nonnull String firstName);

    /** @return value of last name */
    @Nonnull String getLastName();

    /**
     * Sets last name of employee.
     *
     * @param lastName name which need set
     */
    void setLastName(@Nonnull String lastName);

    /**
     * Sets address of employee.
     *
     * @param address which need set
     */
    void setAddress(@Nonnull Address address);

    /** @return value of employee's address */
    @Nullable Address getAddress();
}
