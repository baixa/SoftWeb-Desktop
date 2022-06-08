package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.services.DataService;

public class Registration {

    public static void register(String username, String fullName, String password) {

        Developer registeredDeveloper = new Developer();
        registeredDeveloper.setUsername(username);
        registeredDeveloper.setPassword(password);
        registeredDeveloper.setFullName(fullName);
        DataService.saveDeveloper(registeredDeveloper);

        Authorization.authorize(username, password);
    }

}
