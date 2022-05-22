package com.softweb.desktop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBEntity {

    private static Connection connection;

    public static Connection getConnection(){
        try {
            if(connection == null)
                connection = DriverManager.getConnection("jdbc:mariadb://45.153.230.50:3306/softwebdb", "softwebuser", "SoftWUser");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return connection;
    }
}
