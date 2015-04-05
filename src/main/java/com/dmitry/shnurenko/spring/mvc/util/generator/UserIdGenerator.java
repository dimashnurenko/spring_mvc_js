package com.dmitry.shnurenko.spring.mvc.util.generator;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * Generates unique key for user
 *
 * @author Dmitry Shnurenko
 */
@Component("userIdGenerator")
public class UserIdGenerator implements Generator {

    /** {inheritDoc} */
    @Nonnull
    @Override
    public String generate() {
        StringBuilder result = new StringBuilder();

        final String foundation = "abcdefghjklmnopqrstuvwxyz1234567890";

        for (int i = 0; i < foundation.length(); i++) {
            char character = foundation.charAt((int) (Math.random() * foundation.length()));

            result.append(character);
        }

        return result.toString();
    }
}
