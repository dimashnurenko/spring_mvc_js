package com.dmitry.shnurenko.spring.mvc.dao.employee;

import com.dmitry.shnurenko.spring.mvc.entity.employees.Employee;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
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
    @Nonnull List<Employee> getAllEmployees() throws DBException;

    /**
     * Gets current employee from database via id.
     *
     * @param id this id needs to get employee from database. It is a primary key which allows find employee in database
     * @return an instance of {@link Employee}
     */
    @Nullable Employee getById(@Nonnegative int id) throws DBException;

    /**
     * Saves employee to database and returns boolean value which defines saving state.
     *
     * @param employee employee which need save
     * @return <code>true</code> if employee is saved successfully, and <code>false</code> if exception is thrown
     */
    boolean saveOrUpdate(@Nonnull Employee employee) throws DBException, SQLException;

    /**
     * Deletes current employee from database.
     *
     * @param employee employee which need delete
     */
    void delete(@Nonnull Employee employee) throws DBException;

}
