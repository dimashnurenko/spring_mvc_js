package com.dmitry.shnurenko.spring.mvc.util;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shnurenko
 */
public class RequestUtil {

    private RequestUtil() {
    }

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

    public static void main(String[] args) {
        System.out.println(Integer.parseInt(""));
    }
}
