package com.dmitry.shnurenko.spring.mvc.dao.moreinfo;

import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;

/**
 * Provides methods which allows store information of employee's address.
 *
 * @author Dmitry Shnurenko
 */
public interface AddressDao {

    /**
     * Save address to data base for current employee using employee id. The method can throw {@link DBException}
     *
     * @param employeeId employee id for which need save address
     * @throws DBException
     */
    void saveOrUpdate(@Nonnegative int employeeId, @Nonnull Address address) throws DBException, SQLException;

    /**
     * Returns address to data base for current employee using employee id. The method can throw {@link DBException}
     *
     * @param employeeId employee id for which need save address
     * @return an instance of {@link Address} for current employee using id
     * @throws DBException
     */
    @Nullable Address get(@Nonnegative int employeeId) throws DBException;

    /**
     * Deletes address to data base for current employee using employee id. The method can throw {@link DBException}
     *
     * @param employeeId employee id for which need save address
     * @throws DBException
     */
    void delete(@Nonnegative int employeeId) throws DBException;
}
