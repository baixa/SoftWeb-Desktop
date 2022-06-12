package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;

import java.util.Date;
import java.util.List;

public class Authorization {

    private static Developer currentUser;
    private static boolean authorized = false;


    public static boolean authorize(String username, String password) {
        if(authorized)
            return false;

        List<Developer> developers = DBCache.getCache().getDevelopers();
        Developer existedDeveloper = developers.stream().filter(developer -> developer.getUsername().equals(username)).findFirst().orElse(null);

        if (existedDeveloper == null || !existedDeveloper.getPassword().equals(password))
            return false;
        else {
            currentUser = existedDeveloper;
            currentUser.setLastEntered(new Date());
            DataService.saveDeveloper(currentUser);
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
