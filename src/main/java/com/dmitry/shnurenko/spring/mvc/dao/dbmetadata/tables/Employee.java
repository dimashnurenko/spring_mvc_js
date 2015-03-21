package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
public enum Employee {

    ID("id"), NAME("name");

    private final String columnName;

    Employee(@Nonnull String columnName) {
        this.columnName = columnName;
    }

    @Override
    @Nonnull
    public String toString() {
        return columnName;
    }
}
