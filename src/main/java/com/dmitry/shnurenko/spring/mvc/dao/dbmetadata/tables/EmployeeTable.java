package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables;

import javax.annotation.Nonnull;

/**
 * Contains meta information about employee table in database. ID it's a primary key.
 *
 * @author Dmitry Shnurenko
 */
public enum EmployeeTable {

    ID("id"), FIRST_NAME("first_name"), LAST_NAME("last_name");

    private final String columnName;

    EmployeeTable(@Nonnull String columnName) {
        this.columnName = columnName;
    }

    /** @return name of columns in data base */
    @Override
    @Nonnull
    public String toString() {
        return columnName;
    }
}
