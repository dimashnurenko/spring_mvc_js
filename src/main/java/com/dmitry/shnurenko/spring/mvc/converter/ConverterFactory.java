package com.dmitry.shnurenko.spring.mvc.converter;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * The factory which allows create different converters.
 *
 * @author Dmitry Shnurenko
 */
@Component("converterFactory")
public class ConverterFactory<T> {

    /**
     * Creates entity of converter which can set parameters from request to object.
     *
     * @param object object in which parameters will be set
     * @return an instance of {@link ObjectConverter}
     */
    @Nonnull
    public ObjectConverter<T> createConverter(@Nonnull T object) {
        return new ObjectConverter<>(object);
    }
}
