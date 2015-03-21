package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
public interface DBInfo {

    String getQuery(@Nonnull Queries queries);

}
