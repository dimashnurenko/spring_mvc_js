package com.dmitry.shnurenko.spring.mvc.dao.moreinfo;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;
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

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.*;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.AddressTable.*;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressDaoImplTest {

    private static final String SOME_TEXT     = "someText";
    private static final int    ID            = 1;
    private static final int    DEFAULT_HOUSE = 2;
    private static final int    DEFAULT_FLAT  = 3;

    //constructor mocks
    @Mock
    private DBInfo        dbInfo;
    @Mock
    private EntityFactory entityFactory;
    @Mock
    private DBConnection  sqlLiteConnection;

    //additional mocks
    @Mock
    private Address           address;
    @Mock
    private Connection        connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet         resultSet;

    @InjectMocks
    private AddressDaoImpl addressDao;

    @Before
    public void setUp() throws Exception {
        when(dbInfo.getQuery(ADDRESS_GET_ALL_IDS)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(SAVE_ADDRESS)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(UPDATE_ADDRESS)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(GET_ADDRESS)).thenReturn(SOME_TEXT);
        when(dbInfo.getQuery(DELETE_ADDRESS)).thenReturn(SOME_TEXT);

        when(address.getCountry()).thenReturn(COUNTRY.toString());
        when(address.getCity()).thenReturn(CITY.toString());
        when(address.getStreet()).thenReturn(STREET.toString());
        when(address.getHouse()).thenReturn(DEFAULT_HOUSE);
        when(address.getFlat()).thenReturn(DEFAULT_FLAT);

        when(sqlLiteConnection.create()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true)
                              .thenReturn(false);
    }

    @Test(expected = SQLException.class)
    public void sqlExceptionShouldBeThrownWhenWeCanNotSaveOrUpdateAddress() throws Exception {
        //noinspection unchecked
        when(sqlLiteConnection.create()).thenThrow(SQLException.class);

        addressDao.saveOrUpdate(ID, address);

        verify(sqlLiteConnection).close();
        verify(connection).rollback();
    }

    @Test
    public void addressShouldBeSaved() throws Exception {
        when(resultSet.getInt(EMPLOYEE_ID.toString())).thenReturn(2);

        addressDao.saveOrUpdate(ID, address);

        verify(sqlLiteConnection).create();
        verify(connection).setAutoCommit(false);
        verify(dbInfo).getQuery(ADDRESS_GET_ALL_IDS);
        verify(connection, times(2)).prepareStatement(SOME_TEXT);
        verify(preparedStatement).executeQuery();
        verify(dbInfo).getQuery(SAVE_ADDRESS);
        verify(resultSet, times(2)).next();
        verify(resultSet).getInt(EMPLOYEE_ID.toString());
        verify(dbInfo, never()).getQuery(UPDATE_ADDRESS);

        verify(preparedStatement).setInt(6, ID);
        verify(preparedStatement).setString(1, COUNTRY.toString());
        verify(preparedStatement).setString(2, CITY.toString());
        verify(preparedStatement).setString(3, STREET.toString());
        verify(preparedStatement).setInt(4, DEFAULT_HOUSE);
        verify(preparedStatement).setInt(5, DEFAULT_FLAT);

        verify(address).getCountry();
        verify(address).getCity();
        verify(address).getStreet();
        verify(address).getHouse();
        verify(address).getFlat();

        verify(preparedStatement).execute();
        verify(connection).commit();
        verify(sqlLiteConnection).close();
    }

    @Test
    public void addressShouldBeUpdated() throws Exception {
        when(resultSet.getInt(EMPLOYEE_ID.toString())).thenReturn(ID);

        addressDao.saveOrUpdate(ID, address);

        verify(dbInfo).getQuery(UPDATE_ADDRESS);
    }

    @Test(expected = DBException.class)
    public void dbExceptionShouldBeThrownWhenWeGetAddress() throws Exception {
        //noinspection unchecked
        when(sqlLiteConnection.create()).thenThrow(SQLException.class);

        addressDao.get(ID);

        verify(sqlLiteConnection).close();
    }

    @Test
    public void dbExceptionShouldBeThrownWhenWeGotAddress() throws Exception {
        when(resultSet.getString(COUNTRY.toString())).thenReturn(COUNTRY.toString());
        when(resultSet.getString(CITY.toString())).thenReturn(CITY.toString());
        when(resultSet.getString(STREET.toString())).thenReturn(STREET.toString());
        when(resultSet.getInt(HOUSE.toString())).thenReturn(DEFAULT_HOUSE);
        when(resultSet.getInt(FLAT.toString())).thenReturn(DEFAULT_FLAT);

        when(entityFactory.createAddress(COUNTRY.toString(),
                                         CITY.toString(),
                                         STREET.toString(),
                                         DEFAULT_HOUSE,
                                         DEFAULT_FLAT)).thenReturn(address);

        Address testAddress = addressDao.get(ID);

        verify(sqlLiteConnection).create();
        verify(connection).prepareStatement(SOME_TEXT);
        verify(dbInfo).getQuery(GET_ADDRESS);
        verify(preparedStatement).setInt(1, ID);
        verify(preparedStatement).executeQuery();

        verify(resultSet).next();
        verify(resultSet).getString(COUNTRY.toString());
        verify(resultSet).getString(CITY.toString());
        verify(resultSet).getString(STREET.toString());
        verify(resultSet).getInt(HOUSE.toString());
        verify(resultSet).getInt(FLAT.toString());

        verify(entityFactory).createAddress(COUNTRY.toString(),
                                            CITY.toString(),
                                            STREET.toString(),
                                            DEFAULT_HOUSE,
                                            DEFAULT_FLAT);

        verify(sqlLiteConnection).close();

        assertEquals(testAddress, address);
    }

    @Test(expected = DBException.class)
    public void dbExceptionShouldBeThrownWhenWeDeleteAddress() throws Exception {
        //noinspection unchecked
        when(sqlLiteConnection.create()).thenThrow(SQLException.class);

        addressDao.delete(ID);

        verify(sqlLiteConnection).close();
    }

    @Test
    public void addressShouldBeDeleted() throws Exception {
        addressDao.delete(ID);

        verify(sqlLiteConnection).create();
        verify(dbInfo).getQuery(DELETE_ADDRESS);
        verify(connection).prepareStatement(SOME_TEXT);
        verify(preparedStatement).setInt(1, ID);
        verify(preparedStatement).execute();
        verify(sqlLiteConnection).close();
    }
}