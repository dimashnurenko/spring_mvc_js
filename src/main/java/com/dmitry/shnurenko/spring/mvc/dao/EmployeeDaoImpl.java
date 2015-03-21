package com.dmitry.shnurenko.spring.mvc.dao;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.Employee;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import com.dmitry.shnurenko.spring.mvc.util.dbconnection.SqlLiteConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.GET_ALL_EMPLOYEES;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.SAVE_EMPLOYEE;
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

    @Nonnull
    @Override
    public Employee getById(@Nonnegative int id) {
        return null;
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
    public void update(@Nonnull Employee employee) {

    }

    @Override
    public void delete(@Nonnull Employee employee) {

    }
}
