package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.services.DataService;

import java.util.Date;

/**
 * Registration class contains methods and fields of user
 * registration mechanism in the system.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class Registration {

    /**
     * Method perform registration with username, full name and password.
     *
     * It creates new object of Developer class and fill data of this object
     * After saving new Developer in DB, method calls Authorization.authorize method
     *
     * @param username User name (Login)
     * @param fullName Full user name
     * @param password User password
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
