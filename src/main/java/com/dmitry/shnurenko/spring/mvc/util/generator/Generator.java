package com.dmitry.shnurenko.spring.mvc.util.generator;

import javax.annotation.Nonnull;

/**
 * Provides method which allows generate unique ids.
 *
 * @author Dmitry Shnurenko
 */
public interface Generator {

    /**
     * Generates unique id.
     *
     * @return string representation of id.
     */
    @Nonnull String generate();
}
