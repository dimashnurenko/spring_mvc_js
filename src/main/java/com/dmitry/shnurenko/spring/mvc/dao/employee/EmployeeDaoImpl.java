package com.dmitry.shnurenko.spring.mvc.dao.employee;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.employees.Employee;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import com.dmitry.shnurenko.spring.mvc.util.dbconnection.DBConnection;
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

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo.PATH_TO_QUERIES;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.*;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.EmployeeTable.*;

/**
 * The class contains methods which allows save,delete,update employees in database.
 *
 * @author Dmitry Shnurenko
 */
@Component("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    private final DBInfo        dbInfo;
    private final EntityFactory entityFactory;
    private final DBConnection  sqlLiteConnection;

    @Autowired
    public EmployeeDaoImpl(DBInfo dbInfo, EntityFactory entityFactory, DBConnection sqlLiteConnection) {
        this.dbInfo = dbInfo;
        this.dbInfo.readQueriesFromFile(PATH_TO_QUERIES);
        this.entityFactory = entityFactory;
        this.sqlLiteConnection = sqlLiteConnection;
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public List<Employee> getAllEmployees() throws DBException {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection con = sqlLiteConnection.create();

            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(GET_ALL_EMPLOYEES));

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                employees.add(entityFactory.createManager(resultSet.getInt(ID.toString()),
                                                          resultSet.getString(FIRST_NAME.toString()),
                                                          resultSet.getString(LAST_NAME.toString())));
            }
        } catch (SQLException e) {
            sqlLiteConnection.close();

            throw new DBException(e, "Can't get all employees: " + e.getMessage());
        } finally {
            sqlLiteConnection.close();
        }

        return employees;
    }

    /** {inheritDoc} */
    @Nullable
    @Override
    public Employee getById(@Nonnegative int id) throws DBException {
        try {
            Connection con = sqlLiteConnection.create();
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(GET_EMPLOYEE_BY_ID));

            pstmt.setInt(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            int employeeId = resultSet.getInt(ID.toString());
            String firstName = resultSet.getString(FIRST_NAME.toString());
            String lastName = resultSet.getString(LAST_NAME.toString());

            return entityFactory.createManager(employeeId, firstName, lastName);
        } catch (SQLException e) {
            sqlLiteConnection.close();

            throw new DBException(e, "Can't get employee: " + e.getMessage());
        } finally {
            sqlLiteConnection.close();
        }
    }

    /** {inheritDoc} */
    @Override
    public boolean saveOrUpdate(@Nonnull Employee employee) throws DBException, SQLException {
        int employeeId = employee.getId();

        Connection con = sqlLiteConnection.create();
        try {

            con.setAutoCommit(false);

            PreparedStatement getIds = con.prepareStatement(dbInfo.getQuery(EMPLOYEE_GET_ALL_IDS));
            ResultSet resultSet = getIds.executeQuery();

            String nextQuery = dbInfo.getQuery(SAVE_EMPLOYEE);

            while (resultSet.next()) {
                if (employeeId == resultSet.getInt(ID.toString())) {
                    nextQuery = dbInfo.getQuery(UPDATE_EMPLOYEE);

                    break;
                }
            }

            PreparedStatement pstmt = con.prepareStatement(nextQuery);
            pstmt.setInt(3, employeeId);

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());

            pstmt.execute();

            con.commit();

            sqlLiteConnection.close();
            return true;
        } catch (SQLException exception) {
            con.rollback();

            sqlLiteConnection.close();
            throw new DBException(exception, "Can't save address");
        }
    }

    /** {inheritDoc} */
    @Override
    public void delete(@Nonnull Employee employee) throws DBException {
        try {
            Connection con = sqlLiteConnection.create();

            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(DELETE_EMPLOYEE));
            pstmt.setInt(1, employee.getId());

            pstmt.execute();
        } catch (SQLException e) {
            sqlLiteConnection.close();

            throw new DBException(e, "Can't delete employee: " + e.getMessage());
        } finally {
            sqlLiteConnection.close();
        }
    }
}
