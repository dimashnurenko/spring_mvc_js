package com.dmitry.shnurenko.spring.mvc.entity.employees;

import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Stores information about current state of manager. Also the class contains methods which allows change
 * information about manager.
 *
 * @author Dmitry Shnurenko
 */
public class Manager implements Employee {

    private int     id;
    private String  firstName;
    private String  lastName;
    private Address address;

    public Manager(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /** {inheritDoc} */
    @Override
    @Nonnegative
    public int getId() {
        return id;
    }

    /** {inheritDoc} */
    @Override
    public void setId(@Nonnegative int id) {
        this.id = id;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public String getFirstName() {
        return firstName;
    }

    /** {inheritDoc} */
    @Override
    public void setFirstName(@Nonnull String firstName) {
        this.firstName = firstName;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public String getLastName() {
        return lastName;
    }

    /** {inheritDoc} */
    @Override
    public void setLastName(@Nonnull String lastName) {
        this.lastName = lastName;
    }

    /** {inheritDoc} */
    @Override
    public void setAddress(@Nonnull Address address) {
        this.address = address;
    }

    /** {inheritDoc} */
    @Nullable
    @Override
    public Address getAddress() {
        return address;
    }

    /** {inheritDoc} */
    @Override
    public int hashCode() {
        return id;
    }
}
