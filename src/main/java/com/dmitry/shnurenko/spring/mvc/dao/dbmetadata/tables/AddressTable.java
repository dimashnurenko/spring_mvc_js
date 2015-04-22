package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables;

import javax.annotation.Nonnull;

/**
 * Contains meta information about employee table in database. EMPLOYEE_ID it's a foreign key.
 *
 * @author Dmitry Shnurenko
 */
public enum AddressTable {
    EMPLOYEE_ID("employee_id"),
    COUNTRY("country"),
    CITY("city"),
    STREET("street"),
    HOUSE("house"),
    FLAT("flat");

    private final String columnName;

    AddressTable(@Nonnull String columnName) {
        this.columnName = columnName;
    }

    /** @return name of columns in data base */
    @Override
    @Nonnull
    public String toString() {
        return columnName;
    }
}
