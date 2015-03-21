package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
public enum Queries {
    GET_EMPLOYEE_BY_ID("employee.get.by.id"),
    SAVE_EMPLOYEE("employee.save"),
    DELETE_EMPLOYEE("employee.delete"),
    UPDATE_EMPLOYEE("employee.update"),
    GET_ALL_EMPLOYEES("employee.get.all");

    private final String query;

    Queries(@Nonnull String query) {
        this.query = query;
    }

    @Nonnull
    public String getKey() {
        return query;
    }
}
