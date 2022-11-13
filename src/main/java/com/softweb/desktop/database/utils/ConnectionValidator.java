package com.softweb.desktop.database.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class performs a database connection check.
 */
public class ConnectionValidator {

    /**
     * Logger that captures errors and passes them to the console
     */
    private static final Logger logger = LoggerFactory.getLogger(
            ConnectionValidator.class);

    /**
     * The method checks the connection to the database based on the passed connection URL and captures the state in the logger
     *
     * @param dbUrl Database URL
     * @return Connection state
     */
    public static boolean checkConnectionStatus(String dbUrl)
    {
        try {
            logger.info("Check database connection");
            Connection connection = DriverManager.getConnection(dbUrl);
            if (connection != null && !connection.isClosed() && connection.isValid(5)) {
                connection.prepareStatement("SELECT 1");
                logger.info("Database connection success");
                return true;
            }
            else {
                logger.error("Database connection refused");
            }
        }
        catch (SQLException e) {
            logger.error("Database connection error: ", e);
        }
        return false;
    }
}
