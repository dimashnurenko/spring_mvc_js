package com.dmitry.shnurenko.spring.mvc.dao.user;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries;
import com.dmitry.shnurenko.spring.mvc.entity.access.User;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
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

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo.PATH_TO_QUERIES;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    private static final String SOME_TEXT = "someText";

    @Mock
    private DBInfo            dbInfo;
    @Mock
    private DBConnection      sqlLiteConnection;
    @Mock
    private User              user;
    @Mock
    private Connection        connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet         resultSet;

    @InjectMocks
    private UserDaoImpl userDao;

    @Before
    public void setUp() throws Exception {
        when(sqlLiteConnection.create()).thenReturn(connection);

        when(dbInfo.getQuery(Queries.GET_LOGIN_USER)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(Queries.SAVE_USER_TO_DB)).thenReturn(SOME_TEXT);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(user.getLogin()).thenReturn(SOME_TEXT);
        when(user.getEmail()).thenReturn(SOME_TEXT);
        when(user.getPassword()).thenReturn(SOME_TEXT);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void constructorShouldBeVerified() throws Exception {
        verify(dbInfo).readQueriesFromFile(PATH_TO_QUERIES);
    }

    @Test(expected = DBException.class)
    public void dbConnectionShouldBeThrownWhenAddUser() throws Exception {
        //noinspection unchecked
        when(sqlLiteConnection.create()).thenThrow(SQLException.class);

        userDao.save(user);

        verify(sqlLiteConnection).close();
    }

    @Test
    public void userShouldBeSaved() throws Exception {
        userDao.save(user);

        verify(sqlLiteConnection).create();
        verify(dbInfo).getQuery(Queries.SAVE_USER_TO_DB);
        verify(connection).prepareStatement(SOME_TEXT);

        verify(user).getLogin();
        verify(user).getPassword();
        verify(user).getEmail();

        verify(preparedStatement).setString(1, SOME_TEXT);
        verify(preparedStatement).setString(2, SOME_TEXT);
        verify(preparedStatement).setString(3, SOME_TEXT);

        verify(preparedStatement).execute();
        verify(sqlLiteConnection).close();
    }

    @Test(expected = DBException.class)
    public void dbExceptionShouldBeThrownWhenCheckLoginUser() throws Exception {
        //noinspection unchecked
        when(sqlLiteConnection.create()).thenThrow(SQLException.class);

        userDao.isUserLogin(SOME_TEXT, SOME_TEXT);

        verify(sqlLiteConnection).close();
    }

    @Test
    public void isUserLoginShouldBeChecked() throws Exception {
        userDao.isUserLogin(SOME_TEXT, SOME_TEXT);

        verify(sqlLiteConnection).create();
        verify(dbInfo).getQuery(Queries.GET_LOGIN_USER);
        verify(connection).prepareStatement(SOME_TEXT);
        verify(preparedStatement).setString(1, SOME_TEXT);
        verify(preparedStatement).setString(2, SOME_TEXT);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(sqlLiteConnection).close();
    }

}