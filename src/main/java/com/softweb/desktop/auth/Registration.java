package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.algorithms.Encryptor;

public class Registration {

    public static void register(String username, String fullName, String password) {
        String hashedPassword = Encryptor.hashSHA(password);

        Developer registeredDeveloper = new Developer();
        registeredDeveloper.setUsername(username);
        registeredDeveloper.setPassword(hashedPassword);
        registeredDeveloper.setFullName(fullName);
        DataService.saveDeveloper(registeredDeveloper);

        Authorization.authorize(username, password);
    }

}
