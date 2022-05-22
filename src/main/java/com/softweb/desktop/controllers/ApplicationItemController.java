package com.softweb.desktop.controllers;

import com.softweb.desktop.database.entities.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationItemController implements Initializable {

    @FXML
    private ImageView applicationLogo;

    @FXML
    private Label labelApplicationName;

    @FXML
    private Label labelShortDescription;

    @FXML
    private Label labelDeveloper;

    public ApplicationItemController(Application application) {
        this.applicationLogo.setImage(application.getLogo());
        this.labelApplicationName.setText(application.getName());
        this.labelShortDescription.setText(application.getShortDescription());
        this.labelDeveloper.setText(application.getDeveloperFullName());
    }

    public ApplicationItemController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setLogo(Image logo) {
        this.applicationLogo.setImage(logo);
    }

    public void setApplicationName(String name) {
        this.labelApplicationName.setText(name);
    }

    public void setApplicationDescription(String description) {
        this.labelShortDescription.setText(description);
    }

    public void setApplicationDeveloper(String developer) {
        this.labelDeveloper.setText(developer);
    }
}
