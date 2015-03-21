package com.dmitry.shnurenko.spring.mvc.util.dbconnection;

import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Dmitry Shnurenko
 */
public class SqlLiteConnection {

    private final static Logger LOGGER = Logger.getLogger(SqlLiteConnection.class);

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            LOGGER.error("JDBC class not found...", e);
        }
    }

    private static Connection connection;

    @Nonnull
    public static Connection get() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite::resource:sqlite/db.db");

            if (connection == null) {
                throw new IllegalStateException("Connection is null");
            }
        } catch (SQLException e) {
            LOGGER.error(e);

            close(connection);
        }

        return connection;
    }

    public static void close(@Nullable Connection connection) {
        try {
            if (connection != null) {
                connection.close();

                LOGGER.info("connection was closed...");
            }
        } catch (SQLException e) {
            LOGGER.error("Connection can't be closed", e);
        }
    }
}
