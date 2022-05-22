package com.softweb.desktop.auth;

import com.softweb.desktop.database.DBActivity;
import com.softweb.desktop.utils.Encryptor;

import java.util.Map;

public class Authorization {

    private static String currentUser;
    private static boolean isAuthorized = false;


    public static boolean authorize(String username, String password) {
        if(isAuthorized)
            return false;

        DBActivity dbActivity = new DBActivity();
        Map<String, String> users = dbActivity.getDevelopersUsernameAndPassword();

        String hashedPassword = Encryptor.hashSHA(password);

        if (!users.containsKey(username))
            return false;
        else if (!users.get(username).equals(hashedPassword)){
            return false;
        }
        else {
            currentUser = username;
            isAuthorized = true;
            return true;
        }
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
