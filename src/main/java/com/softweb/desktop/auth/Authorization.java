package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;

import java.util.Date;
import java.util.List;

/**
 * Authorization class contains methods and fields of user
 * authorization mechanism in the system.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class Authorization {

    /**
     * Field current user contains reference to the object of
     * the current user authorized in the system
     *
     * @see Developer
     */
    private static Developer currentUser;

    /**
     * Field authorized is indicator of user authorization status
     */
    private static boolean authorized = false;


    /**
     * Database cache designed to perform CRUD operations
     *
     * @see DBCache
     */
    private static final DBCache dbCache = DBCache.getCache();


    /**
     * Method perform authorization with username and password.
     *
     * If system contains authorized user than method return false.
     * otherwise, the entered data is checked for compliance with
     * the data already in the database.
     *
     * If the entered data is correct, the system updates
     * the user's login history and returns true
     *
     * @param username Логин пользователя
     * @param password Пароль пользователя
     * @return boolean value of authorization status
     *
     * @see Developer
     */
    public static boolean authorize(String username, String password) {
        if(authorized)
            return false;

        List<Developer> developers = dbCache.getDevelopers();
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

    /**
     * Method perform current authorized user sign out
     */
    public static void signOut() {
        currentUser = null;
        authorized = false;
    }

    /**
     * Get status of authorization
     *
     * @return value of authorization status
     */
    public static boolean isAuthorized() {
        return authorized;
    }

    /**
     * Get reference to current authorized user if user is logged in.
     * Otherwise returns null
     *
     * @return Authorized user
     */
    public static Developer getCurrentUser() {
        return currentUser;
    }
}
