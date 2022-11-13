package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.DBCache;
import com.softweb.desktop.database.utils.DataService;

import java.util.Date;
import java.util.List;

/**
 * The class contains methods and fields for implementing the authorization mechanism with the system.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class Authorization {

    /**
     * The field contains a reference to an object of the Developer class and corresponds to the current authorized user.
     *
     * @see Developer
     */
    private static Developer currentUser;

    /**
     * Field-indicator corresponding to the state of authorization in the system.
     */
    private static boolean authorized = false;


    /**
     * A database cache for performing CRUD operations.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = DBCache.getCache();


    /**
     * The method performs authorization in the system according to the given login and password.
     *
     * If a user has already logged in to the system, then the method will return false.
     * Otherwise, the entered data is checked against the data contained in the database.
     *
     * If the entered data is correct, the system updates the user's login history and returns true.
     *
     * @param username Login
     * @param password Password
     * @return status of authorization
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
     * The method logs the user out of the system
     */
    public static void signOut() {
        currentUser = null;
        authorized = false;
    }

    /**
     * Get authorization status
     *
     * @return authorization status value
     */
    public static boolean isAuthorized() {
        return authorized;
    }

    /**
     * Get a link to the current authorized user in the system
     *
     * @return Authorized user
     */
    public static Developer getCurrentUser() {
        return currentUser;
    }
}
