package com.softweb.desktop.database;

import com.softweb.desktop.database.entities.Application;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBActivity {

    private Connection connection;


    public DBActivity() {
        this.connection = DBEntity.getConnection();
    }

    public Map<String, String> getDevelopersUsernameAndPassword() {
        Map<String, String> developers = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT Username, Password FROM developers")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                String password = resultSet.getString(2);

                developers.put(username, password);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    public List<String> getDevelopersUsername() {
        List<String> developers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT Username FROM developers")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(1);

                developers.add(username);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    public List<Application> getApplications() {
        List<Application> applications = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT a.ID, a.Name, a.Short_description, a.Description, " +
                "a.Logo_Path, d.FullName, d.Username FROM applications a INNER JOIN developers d ON a.Developer_ID = d.ID")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getString(1));
                application.setName(resultSet.getString(2));
                application.setShortDescription(resultSet.getString(3));
                application.setLongDescription(resultSet.getString(4));
                application.setLogo(getImageByPath(resultSet.getString(5)));
                application.setDeveloperFullName(resultSet.getString(6));
                application.setDeveloperShortName(resultSet.getString(7));

                applications.add(application);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }



    private Image getImageByPath(String path) {
        Image image = new Image(path);
        if(image.isError())
            System.out.println(image.getException().toString());
        return image;
    }
}
