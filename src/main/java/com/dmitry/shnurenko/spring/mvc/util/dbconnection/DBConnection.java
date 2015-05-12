package com.dmitry.shnurenko.spring.mvc.util.dbconnection;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides methods to control data base connection.
 *
 * @author Dmitry Shnurenko
 */
public interface DBConnection {

    /**
     * Creates and returns data base connection using special path to data base. The method can throw
     * {@link SQLException}. If connection isn't created the method throw {@link IllegalStateException}
     *
     * @return an instance of {@link Connection}
     * @throws SQLException
     */
    @Nonnull Connection create() throws SQLException;

    /** The method closes current connection. */
    void close();
}
