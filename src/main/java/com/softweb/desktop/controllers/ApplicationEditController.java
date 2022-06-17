package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.components.menu.*;
import com.softweb.desktop.database.entity.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationEditController implements Initializable {
    @FXML
    public BorderPane rootElement;

    @FXML
    public Label labelMain;

    @FXML
    public Label labelImages;

    @FXML
    public Label labelInstaller;

    @FXML
    public Label labelAdditional;

    private Label currentMenuItem;

    private Application application;

    private ApplicationEditMenuItem currentItemController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemMain.fxml"));
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentMenuItem = labelMain;
        currentMenuItem.setStyle("-fx-text-fill: #0e8420;");
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void generatePage() {
        currentItemController.setApplication(application);
        currentItemController.refreshContent();
    }

    public void openMain() {
        if (currentItemController instanceof ApplicationEditMenuItemMainController)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemMain.fxml"));
        generateCenterContent(fxmlLoader, labelMain);
    }

    public void openImages() {
        if (currentItemController instanceof ApplicationEditMenuItemImagesController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemImages.fxml"));
        generateCenterContent(fxmlLoader, labelImages);
    }

    private void generateCenterContent(FXMLLoader fxmlLoader, Label labelImages) {
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        generatePage();

        currentMenuItem.setStyle("-fx-text-fill: #000;");
        currentMenuItem = labelImages;
        currentMenuItem.setStyle("-fx-text-fill: #0e8420;");
    }

    public void openInstaller() {
        if (currentItemController instanceof ApplicationEditMenuItemInstallerController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemInstaller.fxml"));
        generateCenterContent(fxmlLoader, labelInstaller);
    }

    public void openAdditional() {
        if (currentItemController instanceof ApplicationEditMenuItemAdditionalController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemAdditional.fxml"));
        generateCenterContent(fxmlLoader, labelAdditional);
    }

    private boolean checkingApplicationSaving() {
        if(getApplication().getId() == null || getApplication().getId() < 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
            alert.setTitle("Внимание");
            alert.setHeaderText("Необходимо название!");
            alert.setContentText("Задайте имя приложению и сохраните изменения прежде чем перейти к этому этапу!");
            alert.show();
            return true;
        }
        return false;
    }
}
