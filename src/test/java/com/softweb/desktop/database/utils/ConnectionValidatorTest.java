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
        String url = "jdbc:mariadb://45.67.35.2:3306/softwebdb?user=softweb_admin&password=SoftWUser";
        assertTrue(ConnectionValidator.checkConnectionStatus(url));
    }
}