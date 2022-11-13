package com.softweb.desktop.controllers.components.edit;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.components.edit.menu.*;
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

/**
 * The main controller that allows you to change information about the application.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationEditController implements Initializable {

    /**
     * FXML node containing the root markup element
     */
    @FXML
    public BorderPane rootElement;

    /**
     * FXML node containing the title of the main edit page
     */
    @FXML
    public Label labelMain;

    /**
     * FXML node containing the title of the image editing page
     */
    @FXML
    public Label labelImages;

    /**
     * FXML node containing the name of the installer edit page
     */
    @FXML
    public Label labelInstaller;

    /**
     * FXML node containing the title of the page for editing additional information
     */
    @FXML
    public Label labelAdditional;

    /**
     * FXML node containing the title of the current edit page
     */
    private Label currentMenuItem;

    /**
     * Related application
     */
    private Application application;

    /**
     * The current edit page.
     */
    private ApplicationEditMenuItem currentItemController;

    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
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

    /**
     * Get a related app
     *
     * @return Related application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Set related app
     *
     * @param application Related application
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Display the current page.
     */
    public void generatePage() {
        currentItemController.setApplication(application);
        currentItemController.refreshContent();
    }

    /**
     * Open the main information page.
     */
    public void openMain() {
        if (currentItemController instanceof ApplicationEditMenuItemMainController)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemMain.fxml"));
        generateCenterContent(fxmlLoader, labelMain);
    }

    /**
     * Open image page.
     */
    public void openImages() {
        if (currentItemController instanceof ApplicationEditMenuItemImagesController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemImages.fxml"));
        generateCenterContent(fxmlLoader, labelImages);
    }

    /**
     * Generate edit page.
     */
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

    /**
     * Open the page with installers.
     */
    public void openInstaller() {
        if (currentItemController instanceof ApplicationEditMenuItemInstallerController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemInstaller.fxml"));
        generateCenterContent(fxmlLoader, labelInstaller);
    }

    /**
     * Open page with additional information.
     */
    public void openAdditional() {
        if (currentItemController instanceof ApplicationEditMenuItemAdditionalController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemAdditional.fxml"));
        generateCenterContent(fxmlLoader, labelAdditional);
    }

    /**
     * Checks whether application changes can be saved
     *
     * @return Indicator, permissions to save changes
     */
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
