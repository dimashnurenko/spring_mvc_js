package com.dmitry.shnurenko.spring.mvc.dao.dbmetadata;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Dmitry Shnurenko
 */
@Component("query")
public class DBInfoImpl implements DBInfo {

    private static final String PATH_TO_QUERIES = "/db/metadata/queries.txt";

    private static final Logger LOGGER = Logger.getLogger(DBInfoImpl.class);

    private final Map<String, String> queries;

    public DBInfoImpl() {
        this.queries = new HashMap<>();

        try {
            URL url = getClass().getResource(PATH_TO_QUERIES);

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
            result.append(line);
        }

        String[] queries = result.toString().split(";");

        for (String query : queries) {
            int separator = query.indexOf("=");

            String key = query.substring(0, separator);
            String value = query.substring(separator + 1);

            this.queries.put(key, value);
        }

    }

    @Override
    public String getQuery(@Nonnull Queries query) {
        String value = this.queries.get(query.getKey());

        if (value == null) {
            throw new NoSuchElementException("key not found. Enter the correct key...");
        }

        return value;
    }
}
