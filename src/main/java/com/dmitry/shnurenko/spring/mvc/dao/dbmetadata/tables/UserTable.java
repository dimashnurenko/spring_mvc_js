package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata.tables;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
public enum UserTable {
    ID("id"), LOGIN("login"), EMAIL("email"), PASSWORD("password");

    private final String columName;

    UserTable(@Nonnull String columnName) {
        this.columName = columnName;
    }

    @Override
    public String toString() {
        return columName;
    }
}
