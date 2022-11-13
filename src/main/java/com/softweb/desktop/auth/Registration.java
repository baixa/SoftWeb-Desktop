package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.DataService;

import java.util.Date;

/**
 * Registration class contains methods and fields to implement
 * mechanism for registering users in the system
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class Registration {

    /**
     * The method performs user registration according to
     * dedicated login, name and password.
     *
     * The method creates an object of the Developer class based on the passed data and
     * then stores it in the database. After creating a user, the system
     * authorizes it.
     *
     * @param username Login
     * @param fullName User full name
     * @param password Password
     *
     * @see Developer
     * @see Authorization#authorize(String, String)
     */
    public static void register(String username, String fullName, String password) {

        Developer registeredDeveloper = new Developer();
        registeredDeveloper.setUsername(username);
        registeredDeveloper.setPassword(password);
        registeredDeveloper.setFullName(fullName);
        registeredDeveloper.setLastEntered(new Date());
        DataService.saveDeveloper(registeredDeveloper);

        Authorization.authorize(username, password);
    }

}
