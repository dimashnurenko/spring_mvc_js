package com.dmitry.shnurenko.spring.mvc.inject;

import com.dmitry.shnurenko.spring.mvc.entity.Employee;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author Dmitry Shnurenko
 */
@Component("entityFactory")
public class EntityFactory {

    @Nonnull
    public Employee createEmployee(@Nonnegative int id,
                                   @Nonnull String name) {
        return new Employee(id, name);
    }
}
