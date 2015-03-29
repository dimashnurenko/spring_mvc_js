package com.dmitry.shnurenko.spring.mvc.dao;

import com.dmitry.shnurenko.spring.mvc.entity.Employee;
import com.sun.istack.internal.Nullable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * Provides methods which allows save,update,delete or get information about employee from data base.
 *
 * @author Dmitry Shnurenko
 */
public interface EmployeeDao {

    /**
     * Gets list of employees from database.
     *
     * @return list which contains employees from database
     */
    @Nonnull List<Employee> getAllEmployees();

    /**
     * Gets current employee from database via id.
     *
     * @param id this id needs to get employee from database. It is a primary key which allows find employee in database
     * @return an instance of {@link Employee}
     */
    @Nullable Employee getById(@Nonnegative int id);

    /**
     * Saves employee to database and returns boolean value which defines saving state.
     *
     * @param employee employee which need save
     * @return <code>true</code> if employee is saved successfully, and <code>false</code> if exception is thrown
     */
    boolean save(@Nonnull Employee employee);

    /**
     * Updates current employee in database.
     *
     * @param employee employee which need update
     * @return an instance of updated {@link Employee}
     */
    @Nonnull
    Employee update(@Nonnull Employee employee);

    /**
     * Deletes current employee from database.
     *
     * @param employee employee which need delete
     * @return an instance of deleted {@link Employee}
     */
    @Nonnull
    Employee delete(@Nonnull Employee employee);

}
