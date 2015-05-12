package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata;

import javax.annotation.Nonnull;

/**
 * Provides methods to get queries to get access to data from database.
 *
 * @author Dmitry Shnurenko
 */
public interface DBInfo {

    String PATH_TO_QUERIES = "/database/queries.txt";

    /**
     * Reads all queries from special file.
     *
     * @param pathToFile path to file which store queries
     */
    void readQueriesFromFile(@Nonnull String pathToFile);

    /**
     * Gets query form special file using special key which stored in query enum.
     *
     * @param queries special key to get query
     * @return string representation of query
     */
    @Nonnull String getQuery(@Nonnull Queries queries);
}
