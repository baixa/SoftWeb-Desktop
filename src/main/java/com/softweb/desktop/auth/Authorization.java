package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.repositories.DeveloperRepository;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.algorithms.Encryptor;

public class Authorization {

    private static Developer currentUser;
    private static boolean authorized = false;
    private static DeveloperRepository developerRepository;


    public static boolean authorize(String username, String password) {
        if(authorized)
            return false;

        developerRepository = DataService.getDeveloperRepository();
        Developer developer = developerRepository.findByUsername(username).stream().findFirst().orElse(null);

        String hashedPassword = Encryptor.hashSHA(password);

        if (developer == null || !developer.getPassword().equals(hashedPassword))
            return false;
        else {
            currentUser = developer;
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
