package com.dmitry.shnurenko.spring.mvc.util.dbconnection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Dmitry Shnurenko
 */
@Component("sqlLiteConnection")
public class SqlLiteConnection implements DBConnection{
    private static final String PATH_TO_DB = "jdbc:sqlite::resource:sqlite/db.db";

    private final static Logger LOGGER = Logger.getLogger(SqlLiteConnection.class);

    private Connection connection;

    public SqlLiteConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            LOGGER.error("JDBC class not found...", e);
        }
    }

    /** {inheritDoc} */
    @Nonnull
    @Override
    public Connection create() throws SQLException {
        connection = DriverManager.getConnection(PATH_TO_DB);

        if (connection == null) {
            throw new IllegalStateException("Connection is null");
        }

        return connection;
    }

    /** {inheritDoc} */
    @Override
    public void close() {
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
