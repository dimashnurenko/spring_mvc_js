package com.dmitry.shnurenko.spring.mvc.converter;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * The class allows convert Json to Java objects. To correct converting name of json parameters must match with
 * fields in Java object.
 *
 * @author Dmitry Shnurenko
 */
public class ObjectConverter<T> {

    private T object;

    public ObjectConverter(@Nonnull T object) {
        this.object = object;
    }

    public T getObjectFrom(@Nonnull HttpServletRequest request) throws IllegalAccessException {
        Map<String, String[]> parameters = request.getParameterMap();

        Class clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                String fieldName = getFieldName(entry.getKey());
                String value = entry.getValue()[0];

                if (fieldName.equals(field.getName()) && !value.isEmpty()) {
                    field.set(object, value);
                }
            }
        }

        return object;
    }

    @Nonnull
    private String getFieldName(@Nonnull String key) {
        if (!key.contains("[")) {
            return key;
        }

        int starIndex = key.indexOf('[') + 1;
        int endIndex = key.lastIndexOf(']');

        return key.substring(starIndex, endIndex);
    }
}
