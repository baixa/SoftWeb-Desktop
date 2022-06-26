package com.softweb.desktop.auth;

import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.DBCache;
import com.softweb.desktop.database.utils.DataService;

import java.util.Date;
import java.util.List;

/**
 * Класс содержит методы и поля для реализации механизма
 * авторизации с системе.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public class Authorization {

    /**
     * Поле содержит ссылку на объект класса Developer и соответствует
     * текущему авторизованному пользователю.
     *
     * @see Developer
     */
    private static Developer currentUser;

    /**
     * Поле-индикатор, соответсвующий состоянию авторизации в системе.
     */
    private static boolean authorized = false;


    /**
     * Кэш базы данных, предназначенный для выполнений CRUD операций.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = DBCache.getCache();


    /**
     * Метод выполняет авторизацию в системе, согласно переданному логину и паролю.
     *
     * Если в системе уже авторизовался пользователь, тогда метод вернет значение false.
     * В ином случае, введенные данные проверяются на соответствие данным, содержащихся в БД.
     *
     * Если введенные данные верны, система обновляет историю входа пользователя и возвращает true.
     *
     * @param username Логин пользователя
     * @param password Пароль пользователя
     * @return статус авторизации
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
     * Метод выполняет выход пользователя из системы
     */
    public static void signOut() {
        currentUser = null;
        authorized = false;
    }

    /**
     * Получить статус авторизации
     *
     * @return значение статуса авторизации
     */
    public static boolean isAuthorized() {
        return authorized;
    }

    /**
     * Получить ссылку на текущего авторизованного в системе пользователя
     *
     * @return Авторизованный пользователь
     */
    public static Developer getCurrentUser() {
        return currentUser;
    }
}
