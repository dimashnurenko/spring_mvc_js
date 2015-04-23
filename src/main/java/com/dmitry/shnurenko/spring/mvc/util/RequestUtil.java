package com.dmitry.shnurenko.spring.mvc.util;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The class contains methods to get parameters from {@link HttpServletRequest} and convert them in more read-able form.
 *
 * @author Dmitry Shnurenko
 */
public class RequestUtil {
    /** Can't create object from this class */
    private RequestUtil() {
    }

    /**
     * Returns map with parameters from {@link HttpServletRequest}
     *
     * @param request request from which need get parameters
     * @return map with parameters
     */
    @Nonnull
    public static Map<String, String> getParameterMap(@Nonnull HttpServletRequest request) {
        Map<String, String[]> requestParameters = request.getParameterMap();

        Map<String, String> parameters = new HashMap<>();

        for (Map.Entry<String, String[]> entry : requestParameters.entrySet()) {
            String key = getKey(entry.getKey());
            String value = entry.getValue()[0];

            parameters.put(key, value);
        }

        return parameters;
    }

    @Nonnull
    private static String getKey(@Nonnull String key) {
        if (!key.contains("[")) {
            return key;
        }

        int startChar = key.indexOf("[") + 1;
        int endChar = key.lastIndexOf("]");

        return key.substring(startChar, endChar);
    }
}
