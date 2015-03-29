package com.dmitry.shnurenko.spring.mvc.inject;

import com.dmitry.shnurenko.spring.mvc.entity.Employee;
import com.dmitry.shnurenko.spring.mvc.entity.Manager;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Factory which allows create entity of employees.
 *
 * @author Dmitry Shnurenko
 */
@Component("entityFactory")
public class EntityFactory {

    /**
     * Creates entity of manager using inputting parameters.
     *
     * @param id        id which need set to manager
     * @param firstName first name which need set to manager
     * @param lastName  last name  which need set to manager
     * @return an instance of {@link Manager}
     */
    @Nonnull
    public Employee createManager(@Nonnegative int id, @Nonnull String firstName, @Nonnull String lastName) {
        return new Manager(id, firstName, lastName);
    }
}
