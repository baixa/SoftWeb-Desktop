package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.components.*;
import com.softweb.desktop.database.entity.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

    public void save() {
        currentItemController.saveEdits();
    }

    public void openMain(MouseEvent mouseEvent) {
        if (currentItemController instanceof ApplicationEditMenuItemMainController)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemMain.fxml"));
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        generatePage();

        currentMenuItem.setStyle("-fx-text-fill: #000;");
        currentMenuItem = labelMain;
        currentMenuItem.setStyle("-fx-text-fill: #0e8420;");
    }

    public void openImages(MouseEvent mouseEvent) {
        if (currentItemController instanceof ApplicationEditMenuItemImagesController)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemImages.fxml"));
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

    public void openInstaller(MouseEvent mouseEvent) {
        if (currentItemController instanceof ApplicationEditMenuItemInstallerController)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemInstaller.fxml"));
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        generatePage();

        currentMenuItem.setStyle("-fx-text-fill: #000;");
        currentMenuItem = labelInstaller;
        currentMenuItem.setStyle("-fx-text-fill: #0e8420;");
    }

    public void openAdditional(MouseEvent mouseEvent) {
        if (currentItemController instanceof ApplicationEditMenuItemAdditionalController)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemAdditional.fxml"));
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        generatePage();

        currentMenuItem.setStyle("-fx-text-fill: #000;");
        currentMenuItem = labelAdditional;
        currentMenuItem.setStyle("-fx-text-fill: #0e8420;");
    }
}
