package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.DataService;

import java.util.Date;

/**
 * Класс регистрации содержит методы и поля для реализации
 * механизма регистрации пользователей в системе
 *
 * @author Максимчук И.
 * @version 1.0
 */
public class Registration {

    /**
     * Метод выполняет регистрацию пользователя согласно
     * преданному логину, имени и паролю.
     *
     * Метод создает объект класса Developer на основе переданных данных и
     * затем сохраняет его в базе данных. После создания пользователя система
     * авторизует его.
     *
     * @param username Логин пользователя
     * @param fullName Имя пользователя
     * @param password Пароль пользователя
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
