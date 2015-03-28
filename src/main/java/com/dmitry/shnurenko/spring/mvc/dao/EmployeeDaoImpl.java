package com.dmitry.shnurenko.spring.mvc.dao;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.Employee;
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
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.Employee.ID;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.Employee.NAME;
import static com.dmitry.shnurenko.spring.mvc.util.dbconnection.SqlLiteConnection.close;

/**
 * @author Dmitry Shnurenko
 */
@Component("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    private final DBInfo dbInfo;
    private final EntityFactory entityFactory;

    @Autowired
    public EmployeeDaoImpl(DBInfo dbInfo, EntityFactory entityFactory) {
        this.dbInfo = dbInfo;
        this.entityFactory = entityFactory;
    }

    @Nonnull
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(GET_ALL_EMPLOYEES));

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                employees.add(entityFactory.createEmployee(resultSet.getInt(ID.toString()),
                                                           resultSet.getString(NAME.toString())));
            }
        } catch (SQLException e) {
            close(con);
        } finally {
            close(con);
        }

        return employees;
    }

    @Nullable
    @Override
    public Employee getById(@Nonnegative int id) {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(GET_EMPLOYEE_BY_ID));

            pstmt.setInt(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            int employeeId = resultSet.getInt(ID.toString());
            String name = resultSet.getString(NAME.toString());

            return entityFactory.createEmployee(employeeId, name);
        } catch (SQLException e) {
            close(con);

            return null;
        } finally {
            close(con);
        }
    }

    @Override
    public boolean save(@Nonnull Employee employee) {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(SAVE_EMPLOYEE));
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getName());

            pstmt.execute();

            return true;
        } catch (SQLException e) {
            close(con);

            return false;
        } finally {
            close(con);
        }
    }

    @Override
    public Employee update(@Nonnull Employee employee) {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(UPDATE_EMPLOYEE));
            pstmt.setString(1, employee.getName());
            pstmt.setInt(2, employee.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            close(con);
        } finally {
            close(con);
        }

        return employee;
    }

    @Override
    public Employee delete(@Nonnull Employee employee) {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(DELETE_EMPLOYEE));
            pstmt.setInt(1, employee.getId());

            pstmt.execute();

        } catch (SQLException e) {
            close(con);
        } finally {
            close(con);
        }

        return employee;
    }
}
