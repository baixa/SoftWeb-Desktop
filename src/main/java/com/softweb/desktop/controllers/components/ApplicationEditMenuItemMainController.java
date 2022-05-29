package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entity.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationEditMenuItemMainController extends ApplicationEditMenuItem implements Initializable{
    @FXML
    public TextField tbAppName;

    @FXML
    public TextField tbShortDescription;

    @FXML
    public TextArea tbDescription;

    @FXML
    public ImageView ivLogo;

    private Application application;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public void save() {
        this.application.setName(tbAppName.textProperty().getValue());
        this.application.setShortDescription(tbShortDescription.textProperty().getValue());
        this.application.setDescription(tbDescription.textProperty().getValue());
    }

    public void refreshContent() {
        this.tbAppName.textProperty().setValue(application.getName());
        this.tbShortDescription.textProperty().setValue(application.getShortDescription());
        this.tbDescription.textProperty().setValue(application.getDescription());
        this.ivLogo.setImage(new Image(application.getLogoPath()));
    }
}
