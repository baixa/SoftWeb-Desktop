package com.softweb.desktop.database.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса ConnectionValidator
 */
class ConnectionValidatorTest {

    /**
     * Тест на соединение по некорректному URL
     */
    @Test
    void checkConnectionStatusNegative() {
        String url = "Empty URL";
        assertFalse(ConnectionValidator.checkConnectionStatus(url));
    }

    /**
     * Тест на соединение по правильному URL
     */
    @Test
    void checkConnectionStatusPositive() {
        String url = "jdbc:mariadb://127.0.0.1:3306/softwebdb?user=softweb_admin&password=SoftWUser";
        assertTrue(ConnectionValidator.checkConnectionStatus(url));
    }
}