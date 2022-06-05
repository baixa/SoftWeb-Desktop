package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.repositories.DeveloperRepository;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.algorithms.Encryptor;

import java.util.List;

public class Authorization {

    private static Developer currentUser;
    private static boolean authorized = false;


    public static boolean authorize(String username, String password) {
        if(authorized)
            return false;

        List<Developer> developers = DBCache.getCache().getDevelopers();
        Developer existedDeveloper = developers.stream().filter(developer -> developer.getUsername().equals(username)).findFirst().orElse(null);

        String hashedPassword = Encryptor.hashSHA(password);

        if (existedDeveloper == null || !existedDeveloper.getPassword().equals(hashedPassword))
            return false;
        else {
            currentUser = existedDeveloper;
            authorized = true;
            return true;
        }
    }

    public static void unauthorize() {
        currentUser = null;
        authorized = false;
    }

    public static boolean isAuthorized() {
        return authorized;
    }

    public static Developer getCurrentUser() {
        return currentUser;
    }
}
