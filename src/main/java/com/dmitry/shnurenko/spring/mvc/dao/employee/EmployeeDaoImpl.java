package com.dmitry.shnurenko.spring.mvc.dao.employee;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.employees.Employee;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import com.dmitry.shnurenko.spring.mvc.util.dbconnection.SqlLiteConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.*;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.EmployeeTable.*;
import static com.dmitry.shnurenko.spring.mvc.util.dbconnection.SqlLiteConnection.close;

/**
 * The class contains methods which allows save,delete,update employees in database.
 *
 * @author Dmitry Shnurenko
 */
@Component("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    private final DBInfo        dbInfo;
    private final EntityFactory entityFactory;

    @Autowired
    public EmployeeDaoImpl(DBInfo dbInfo, EntityFactory entityFactory) {
        this.dbInfo = dbInfo;
        this.entityFactory = entityFactory;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public List<Employee> getAllEmployees() throws DBException {
        List<Employee> employees = new ArrayList<>();

        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(GET_ALL_EMPLOYEES));

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                employees.add(entityFactory.createManager(resultSet.getInt(ID.toString()),
                                                          resultSet.getString(FIRST_NAME.toString()),
                                                          resultSet.getString(LAST_NAME.toString())));
            }
        } catch (SQLException e) {
            close(con);

            throw new DBException(e, "Can't get all employees: " + e.getMessage());
        } finally {
            close(con);
        }

        return employees;
    }

    /** {inheritDoc} */
    @Nullable
    @Override
    public Employee getById(@Nonnegative int id) throws DBException {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(GET_EMPLOYEE_BY_ID));

            pstmt.setInt(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            int employeeId = resultSet.getInt(ID.toString());
            String firstName = resultSet.getString(FIRST_NAME.toString());
            String lastName = resultSet.getString(LAST_NAME.toString());

            return entityFactory.createManager(employeeId, firstName, lastName);
        } catch (SQLException e) {
            close(con);

            throw new DBException(e, "Can't get employee: " + e.getMessage());
        } finally {
            close(con);
        }
    }

    /** {inheritDoc} */
    @Override
    public boolean save(@Nonnull Employee employee) throws DBException {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(SAVE_EMPLOYEE));
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());

            pstmt.execute();

            return true;
        } catch (SQLException e) {
            close(con);

            throw new DBException(e, "Can't save employee: " + e.getMessage());
        } finally {
            close(con);
        }
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public Employee update(@Nonnull Employee employee) throws DBException {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(UPDATE_EMPLOYEE));
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setInt(3, employee.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            close(con);

            throw new DBException(e, "Can't update employee: " + e.getMessage());
        } finally {
            close(con);
        }

        return employee;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public Employee delete(@Nonnull Employee employee) throws DBException {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(DELETE_EMPLOYEE));
            pstmt.setInt(1, employee.getId());

            pstmt.execute();

        } catch (SQLException e) {
            close(con);

            throw new DBException(e, "Can't delete employee: " + e.getMessage());
        } finally {
            close(con);
        }

        return employee;
    }
}
