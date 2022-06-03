package com.softweb.desktop.controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void saveEdits() {
        getApplication().setName(tbAppName.textProperty().getValue());
        getApplication().setShortDescription(tbShortDescription.textProperty().getValue());
        getApplication().setDescription(tbDescription.textProperty().getValue());

        updateApplication();
    }

    @Override
    public void refreshContent() {
        this.tbAppName.textProperty().setValue(getApplication().getName());
        this.tbShortDescription.textProperty().setValue(getApplication().getShortDescription());
        this.tbDescription.textProperty().setValue(getApplication().getDescription());
        this.ivLogo.setImage(getApplication().getLogo());
    }
}
