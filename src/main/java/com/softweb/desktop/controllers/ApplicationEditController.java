package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.components.ApplicationEditMenuItem;
import com.softweb.desktop.database.entity.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationEditController implements Initializable {
    @FXML
    public BorderPane rootElement;

    private Application application;

    private Initializable currentItemController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemAdditional.fxml"));
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void generatePage() {
        ((ApplicationEditMenuItem)currentItemController).setApplication(application);
        ((ApplicationEditMenuItem)currentItemController).refreshContent();
    }

    public void save() {
        if(currentItemController instanceof ApplicationEditMenuItem)
            ((ApplicationEditMenuItem) currentItemController).saveEdits();
    }
}
