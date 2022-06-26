package com.softweb.desktop.database.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс выполняет проверку соединения к базе данных.
 */
public class ConnectionValidator {

    /**
     * Логер, фиксирующий ошибки и передающий их в консоль
     */
    private static final Logger logger = LoggerFactory.getLogger(
            ConnectionValidator.class);

    /**
     * Метод проверяет соединение к базе данных на основе переданного URL соединения и фиксирующий состояние в логгере
     * @param dbUrl URL базы данных
     */
    public static void checkConnectionStatus(String dbUrl)
    {
        try {
            logger.info("Check database connection");
            Connection connection = DriverManager.getConnection(dbUrl);
            if (connection != null && !connection.isClosed() && connection.isValid(5)) {
                connection.prepareStatement("SELECT 1");
                logger.info("Database connection success");
            }
            else {
                logger.error("Database connection refused");
            }
        }
        catch (SQLException e) {
            logger.error("Database connection error: ", e);
        }
    }
}
