package com.dmitry.shnurenko.spring.mvc.dao.moreinfo;

import com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.DBInfo;
import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import com.dmitry.shnurenko.spring.mvc.util.dbconnection.SqlLiteConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.GET_ADDRESS;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.Queries.SAVE_ADDRESS;
import static com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables.AddressTable.*;
import static com.dmitry.shnurenko.spring.mvc.util.dbconnection.SqlLiteConnection.close;

/**
 * The class contains methods which allows store employee's address.
 *
 * @author Dmitry Shnurenko
 */
@Component("addressDao")
public class AddressDaoImpl implements AddressDao {

    private final DBInfo        dbInfo;
    private final EntityFactory entityFactory;

    @Autowired
    public AddressDaoImpl(DBInfo dbInfo, EntityFactory entityFactory) {
        this.dbInfo = dbInfo;
        this.entityFactory = entityFactory;
    }

    /** {inheritDoc} */
    @Override
    public void save(@Nonnegative int employeeId, @Nonnull Address address) throws DBException {
        Connection con = SqlLiteConnection.get();

        try {
            PreparedStatement pstmt = con.prepareStatement(dbInfo.getQuery(SAVE_ADDRESS));
            pstmt.setInt(1, employeeId);

            pstmt.setString(2, address.getCountry());
            pstmt.setString(3, address.getCity());
            pstmt.setString(4, address.getStreet());
            pstmt.setInt(5, address.getHouse());
            pstmt.setInt(6, address.getFlat());

            pstmt.execute();

        } catch (SQLException exception) {
            throw new DBException(exception, "Can't save address");
        }
    }

    /** {inheritDoc} */
    @Override
    @Nullable
    public Address get(@Nonnegative int employeeId) throws DBException {
        Connection con = SqlLiteConnection.get();

        try {
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
            close(con);

            throw new DBException(exception, "Can't get address...");
        } finally {
            close(con);
        }
    }

    /** {inheritDoc} */
    @Override
    public void update(@Nonnegative int employeeId, @Nonnull Address address) throws DBException {

    }

    /** {inheritDoc} */
    @Override
    public void delete(@Nonnegative int employeeId) throws DBException {

    }
}


