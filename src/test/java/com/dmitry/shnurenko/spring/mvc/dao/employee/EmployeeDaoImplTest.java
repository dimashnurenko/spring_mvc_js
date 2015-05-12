package com.dmitry.shnurenko.spring.mvc.dao.employee;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.EmployeeTable;
import com.dmitry.shnurenko.spring.mvc.entity.employees.Employee;
import com.dmitry.shnurenko.spring.mvc.entity.employees.Manager;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import com.dmitry.shnurenko.spring.mvc.util.dbconnection.DBConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo.PATH_TO_QUERIES;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.*;
import static junit.framework.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeDaoImplTest {

    private static final int    ID        = 1;
    private static final String NAME      = "name";
    private static final String LAST_NAME = "lastName";
    private static final String SOME_TEXT = "someText";


    //constructor mocks
    @Mock
    private DBInfo        dbInfo;
    @Mock
    private EntityFactory entityFactory;
    @Mock
    private DBConnection  sqlLiteConnection;

    //additional mocks
    @Mock
    private Manager           manager;
    @Mock
    private Connection        connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet         resultSet;

    @InjectMocks
    private EmployeeDaoImpl employeeDao;

    @Before
    public void setUp() throws Exception {
        when(dbInfo.getQuery(GET_ALL_EMPLOYEES)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(GET_EMPLOYEE_BY_ID)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(EMPLOYEE_GET_ALL_IDS)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(SAVE_EMPLOYEE)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(UPDATE_EMPLOYEE)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(DELETE_EMPLOYEE)).thenReturn(SOME_TEXT);

        when(manager.getId()).thenReturn(ID);
        when(manager.getFirstName()).thenReturn(NAME);
        when(manager.getLastName()).thenReturn(LAST_NAME);

        when(sqlLiteConnection.create()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true)
                              .thenReturn(false);

        when(entityFactory.createManager(anyInt(), anyString(), anyString())).thenReturn(manager);

        when(resultSet.getInt(anyString())).thenReturn(ID);
        when(resultSet.getString(anyString())).thenReturn(NAME).thenReturn(LAST_NAME);
    }

    @Test
    public void constructorShouldBeVerified() throws Exception {
        verify(dbInfo).readQueriesFromFile(PATH_TO_QUERIES);
    }

    @Test(expected = DBException.class)
    public void dataBaseExceptionShouldBeThrownWhenSomethingWrong() throws Exception {
        //noinspection unchecked
        when(connection.prepareStatement(SOME_TEXT)).thenThrow(SQLException.class);

        employeeDao.getAllEmployees();

        verify(sqlLiteConnection).close();
    }

    @Test
    public void listWithEmployeeShouldBeReturned() throws Exception {
        List<Employee> employees = employeeDao.getAllEmployees();

        verify(sqlLiteConnection).create();
        verify(dbInfo).getQuery(GET_ALL_EMPLOYEES);
        verify(connection).prepareStatement(SOME_TEXT);
        verify(preparedStatement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(entityFactory).createManager(ID, NAME, LAST_NAME);

        resultSetShouldBeVerified();

        assertEquals(employees.size(), 1);
        assertSame(employees.get(0), manager);
    }

    private void resultSetShouldBeVerified() throws Exception {
        verify(resultSet).getInt(EmployeeTable.ID.toString());
        verify(resultSet).getString(EmployeeTable.FIRST_NAME.toString());
        verify(resultSet).getString(EmployeeTable.LAST_NAME.toString());
    }

    @Test(expected = DBException.class)
    public void dbExceptionShouldBeThrownWhenCanNotGetById() throws Exception {
        //noinspection unchecked
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

        employeeDao.getById(ID);

        verify(sqlLiteConnection).close();
    }

    @Test
    public void employeeShouldBeGotById() throws Exception {
        Employee employee = employeeDao.getById(ID);

        verify(sqlLiteConnection).create();
        verify(dbInfo).getQuery(GET_EMPLOYEE_BY_ID);
        verify(connection).prepareStatement(SOME_TEXT);
        verify(preparedStatement).setInt(1, ID);
        verify(preparedStatement).executeQuery();

        resultSetShouldBeVerified();

        verify(entityFactory).createManager(ID, NAME, LAST_NAME);

        assertEquals(employee, manager);
    }

    @Test(expected = DBException.class)
    public void saveOrUpdateShouldBeImpossibleWhenSqlExceptionShouldBeThrown() throws Exception {
        //noinspection unchecked
        when(connection.prepareStatement(SOME_TEXT)).thenThrow(SQLException.class);

        employeeDao.saveOrUpdate(manager);

        verify(connection).rollback();
        verify(sqlLiteConnection).close();
    }

    @Test
    public void employeeShouldBeSaved() throws Exception {
        when(resultSet.getInt(EmployeeTable.ID.toString())).thenReturn(2);

        boolean isSaved = employeeDao.saveOrUpdate(manager);

        verify(manager).getId();
        verify(sqlLiteConnection).create();
        verify(connection).setAutoCommit(false);
        verify(dbInfo).getQuery(EMPLOYEE_GET_ALL_IDS);
        verify(connection, times(2)).prepareStatement(SOME_TEXT);
        verify(preparedStatement).executeQuery();
        verify(dbInfo).getQuery(SAVE_EMPLOYEE);
        verify(resultSet, times(2)).next();
        verify(resultSet).getInt(EmployeeTable.ID.toString());
        verify(dbInfo, never()).getQuery(UPDATE_EMPLOYEE);

        verify(preparedStatement).setInt(3, ID);
        verify(preparedStatement).setString(1, NAME);
        verify(preparedStatement).setString(2, LAST_NAME);

        verify(manager).getFirstName();
        verify(manager).getLastName();

        verify(preparedStatement).execute();
        verify(connection).commit();
        verify(sqlLiteConnection).close();

        assertTrue(isSaved);
    }

    @Test
    public void employeeShouldBeUpdated() throws Exception {
        when(resultSet.getInt(EmployeeTable.ID.toString())).thenReturn(ID);

        boolean isUpdated = employeeDao.saveOrUpdate(manager);

        verify(dbInfo).getQuery(UPDATE_EMPLOYEE);

        assertTrue(isUpdated);
    }

    @Test(expected = DBException.class)
    public void dbExceptionShouldBeThrownWhenDeletingImpossible() throws Exception {
        //noinspection unchecked
        when(sqlLiteConnection.create()).thenThrow(SQLException.class);

        employeeDao.delete(manager);

        verify(sqlLiteConnection).close();
        verify(preparedStatement, never()).execute();
    }

    @Test
    public void employeeShouldBeDeleted() throws Exception {
        employeeDao.delete(manager);

        verify(sqlLiteConnection).create();
        verify(dbInfo).getQuery(DELETE_EMPLOYEE);
        verify(connection).prepareStatement(SOME_TEXT);
        verify(preparedStatement).setInt(1, ID);
        verify(preparedStatement).execute();
        verify(sqlLiteConnection).close();
    }
}