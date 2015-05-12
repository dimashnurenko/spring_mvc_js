package com.dmitry.shnurenko.spring.mvc.dao.user;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.access.User;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.util.dbconnection.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo.PATH_TO_QUERIES;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.GET_LOGIN_USER;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.SAVE_USER_TO_DB;

/**
 * Contains business logic which allows save user to database
 *
 * @author Dmitry Shnurenko
 */
@Component("userDao")
public class UserDaoImpl implements UserDao {

    private final DBInfo       dbInfo;
    private final DBConnection sqlLiteConnection;

    @Autowired
    public UserDaoImpl(DBInfo dbInfo, DBConnection sqlLiteConnection) {
        this.dbInfo = dbInfo;
        this.dbInfo.readQueriesFromFile(PATH_TO_QUERIES);
        this.sqlLiteConnection = sqlLiteConnection;
    }

    /** {inheritDoc} */
    @Override
    public void save(@Nonnull User user) throws DBException {
        try {
            Connection con = sqlLiteConnection.create();
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(SAVE_USER_TO_DB));
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());

            pstmt.execute();
        } catch (SQLException e) {
            sqlLiteConnection.close();

            throw new DBException(e, "Can't save user to data base");
        } finally {
            sqlLiteConnection.close();
        }
    }

    /** {inheritDoc} */
    @Override
    public boolean isUserLogin(@Nonnull String login, @Nonnull String password) throws DBException {
        try {
            Connection connection = sqlLiteConnection.create();
            PreparedStatement pstmt = connection.prepareStatement(dbInfo.getQuery(GET_LOGIN_USER));
            pstmt.setString(1, login);
            pstmt.setString(2, password);

            ResultSet resultSet = pstmt.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            sqlLiteConnection.close();

            throw new DBException(e, "Can't get user... " + e.getMessage());
        } finally {
            sqlLiteConnection.close();
        }
    }
}
