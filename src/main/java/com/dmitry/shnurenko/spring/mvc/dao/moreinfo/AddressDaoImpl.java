package com.dmitry.shnurenko.spring.mvc.dao.moreinfo;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;
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

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo.PATH_TO_QUERIES;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.*;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.AddressTable.*;

/**
 * The class contains methods which allows store employee's address.
 *
 * @author Dmitry Shnurenko
 */
@Component("addressDao")
public class AddressDaoImpl implements AddressDao {

    private final DBInfo        dbInfo;
    private final EntityFactory entityFactory;
    private final DBConnection  sqlLiteConnection;

    @Autowired
    public AddressDaoImpl(DBInfo dbInfo, EntityFactory entityFactory, DBConnection sqlLiteConnection) {
        this.dbInfo = dbInfo;
        this.dbInfo.readQueriesFromFile(PATH_TO_QUERIES);
        this.entityFactory = entityFactory;
        this.sqlLiteConnection = sqlLiteConnection;
    }

    /** {inheritDoc} */
    @Override
    public void saveOrUpdate(@Nonnegative int employeeId, @Nonnull Address address) throws DBException, SQLException {
        Connection con = sqlLiteConnection.create();

        try {
            con.setAutoCommit(false);

            PreparedStatement getIds = con.prepareStatement(dbInfo.getQuery(ADDRESS_GET_ALL_IDS));
            ResultSet resultSet = getIds.executeQuery();

            String nextQuery = dbInfo.getQuery(SAVE_ADDRESS);

            while (resultSet.next()) {
                if (employeeId == resultSet.getInt(EMPLOYEE_ID.toString())) {
                    nextQuery = dbInfo.getQuery(UPDATE_ADDRESS);

                    break;
                }
            }

            PreparedStatement pstmt = con.prepareStatement(nextQuery);
            pstmt.setInt(6, employeeId);

            pstmt.setString(1, address.getCountry());
            pstmt.setString(2, address.getCity());
            pstmt.setString(3, address.getStreet());
            pstmt.setInt(4, address.getHouse());
            pstmt.setInt(5, address.getFlat());

            pstmt.execute();

            con.commit();

            sqlLiteConnection.close();
        } catch (SQLException exception) {
            con.rollback();

            sqlLiteConnection.close();
            throw new DBException(exception, "Can't save address");
        }
    }

    /** {inheritDoc} */
    @Override
    @Nullable
    public Address get(@Nonnegative int employeeId) throws DBException {
        try {
            Connection con = sqlLiteConnection.create();
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(GET_ADDRESS));

            pstmt.setInt(1, employeeId);

            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();

            String country = resultSet.getString(COUNTRY.toString());
            String city = resultSet.getString(CITY.toString());
            String street = resultSet.getString(STREET.toString());
            int house = resultSet.getInt(HOUSE.toString());
            int flat = resultSet.getInt(FLAT.toString());

            return entityFactory.createAddress(country, city, street, house, flat);
        } catch (SQLException exception) {
            sqlLiteConnection.close();

            throw new DBException(exception, "Can't get address...");
        } finally {
            sqlLiteConnection.close();
        }
    }

    /** {inheritDoc} */
    @Override
    public void delete(@Nonnegative int employeeId) throws DBException {
        try {
            Connection con = sqlLiteConnection.create();
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(DELETE_ADDRESS));

            pstmt.setInt(1, employeeId);

            pstmt.execute();

            sqlLiteConnection.close();
        } catch (SQLException exception) {
            sqlLiteConnection.close();

            throw new DBException(exception, "Can't delete address");
        }
    }
}


