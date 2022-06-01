package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.services.DataService;
import com.softweb.desktop.utils.algorithms.Encryptor;

import java.util.ArrayList;
import java.util.List;

public class Registration {

    public static void register(String username, String fullName, String password) throws Exception{
        List<Developer> developers = new ArrayList<>();
        DataService.getDeveloperRepository().findAll().forEach(developers::add);

        Developer existedDeveloper = developers.stream()
                                        .filter(developer -> developer.getUsername().equals(username))
                                        .findFirst().orElse(null);

        if(existedDeveloper != null)
            throw new Exception("Пользователь с таким логином уже имеется в системе!");

        String hashedPassword = Encryptor.hashSHA(password);

        Developer registeredDeveloper = new Developer();
        registeredDeveloper.setUsername(username);
        registeredDeveloper.setPassword(hashedPassword);
        registeredDeveloper.setFullName(fullName);
        DataService.getDeveloperRepository().save(registeredDeveloper);

        Authorization.authorize(username, password);
    }

}
