package com.dmitry.shnurenko.spring.mvc.controllers.register;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shnurenko
 */
@Component("userLoginManager")
public class UserLoginManager {
    private final List<String> usersIds;

    public UserLoginManager() {
        usersIds = new ArrayList<>();
    }

    public void addId(@Nonnull String id) {
        usersIds.add(id);
    }

    public boolean isContainId(@Nonnull String id) {
        return usersIds.contains(id);
    }

    public void removeId(@Nonnull String id) {
        usersIds.remove(usersIds.indexOf(id));
    }
}
