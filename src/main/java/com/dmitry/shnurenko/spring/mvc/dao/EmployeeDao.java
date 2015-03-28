package com.dmitry.shnurenko.spring.mvc.dao;

import com.dmitry.shnurenko.spring.mvc.entity.Employee;
import com.sun.istack.internal.Nullable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
public interface EmployeeDao {

    @Nonnull List<Employee> getAllEmployees();

    @Nullable Employee getById(@Nonnegative int id);

    boolean save(@Nonnull Employee employee);

    Employee update(@Nonnull Employee employee);

    Employee delete(@Nonnull Employee employee);

}
