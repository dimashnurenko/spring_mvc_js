package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The implementation of dbInfo. Provides methods to get queries from special file.
 *
 * @author Dmitry Shnurenko
 */
@Component("query")
public class DBInfoImpl implements DBInfo {

    private static final Logger LOGGER = Logger.getLogger(DBInfoImpl.class);

    private final Map<String, String> queries;

    public DBInfoImpl() {
        this.queries = new HashMap<>();
    }

    /** {inheritDoc} */
    @Override
    public void readQueriesFromFile(@Nonnull String pathToFile) {
        try {
            URL url = getClass().getResource(pathToFile);

            if (url == null) {
                throw new NoSuchFileException("can't find file on this path: " + pathToFile);
            }

            Path pathToLocale = Paths.get(url.toURI());
            Charset charset = Charset.defaultCharset();

            List<String> lines = Files.readAllLines(pathToLocale, charset);

            addQueriesToMap(lines);
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("locale couldn't be read...", e);
        }
    }

    private void addQueriesToMap(@Nonnull List<String> lines) {
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if (!line.endsWith(";") && !line.isEmpty()) {
                throw new IllegalArgumentException("String must ends with ';'");
            }
            result.append(line);
        }

        String[] queries = result.toString().split(";");

        for (String query : queries) {
            if (!query.contains("=")) {
                throw new IllegalArgumentException("Must be '=' symbol which defines key and value...");
            }

            int separator = query.indexOf("=");

            String key = query.substring(0, separator);
            String value = query.substring(separator + 1);

            this.queries.put(key, value);
        }

    }

    /** {inheritDoc} */
    @Override
    @Nonnull
    public String getQuery(@Nonnull Queries query) {
        String value = this.queries.get(query.getQuery());

        if (value == null) {
            throw new NoSuchElementException("key not found. Enter the correct key...");
        }

        return value;
    }
}
