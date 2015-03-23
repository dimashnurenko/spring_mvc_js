package com.dmitry.shnurenko.spring.mvc.dao;

import com.dmitry.shnurenko.spring.mvc.entity.Employee;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
public interface EmployeeDao {

    @Nonnull
    List<Employee> getAllEmployees();

    @Nonnull
    Employee getById(@Nonnegative int id);

    boolean save(@Nonnull Employee employee);

    void update(@Nonnull Employee employee);

    boolean delete(@Nonnegative int id);

}
