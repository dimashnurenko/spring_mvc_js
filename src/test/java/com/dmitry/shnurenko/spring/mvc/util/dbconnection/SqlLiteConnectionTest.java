package com.dmitry.shnurenko.spring.mvc.util.dbconnection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class SqlLiteConnectionTest {

    @InjectMocks
    private SqlLiteConnection connection;

    @Test
    public void connectionShouldBeReturned() throws Exception {
        Connection con = connection.get();

        assertThat(con, is(notNullValue()));
    }

}