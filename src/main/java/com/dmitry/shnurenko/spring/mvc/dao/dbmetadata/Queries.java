package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
public enum Queries {
    //    employee
    GET_EMPLOYEE_BY_ID("employee.get.by.id"),
    SAVE_EMPLOYEE("employee.save"),
    DELETE_EMPLOYEE("employee.delete"),
    UPDATE_EMPLOYEE("employee.update"),
    GET_ALL_EMPLOYEES("employee.get.all"),
    //  user
    SAVE_USER_TO_DB("user.save"),
    GET_LOGIN_USER("user.get.if.login"),
    //address
    SAVE_ADDRESS("address.save"),
    UPDATE_ADDRESS("address.update"),
    DELETE_ADDRESS("address.delete"),
    GET_ADDRESS("address.get"),
    GET_ALL_IDS("address.get.all.ids");

    private final String query;

    Queries(@Nonnull String query) {
        this.query = query;
    }

    @Nonnull
    public String getQuery() {
        return query;
    }
}
