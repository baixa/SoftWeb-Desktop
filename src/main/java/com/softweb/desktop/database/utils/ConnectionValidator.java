package com.softweb.desktop.database.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionValidator {

    private static final Logger logger = LoggerFactory.getLogger(
            ConnectionValidator.class);

    public static boolean isConnectionValid()
    {
        try {
            logger.info("Check database connection");
            Connection connection = DriverManager.getConnection("jdbc:mariadb://45.153.230.50:3306/softwebdb?user=softwebuser&password=SoftWUser");
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
