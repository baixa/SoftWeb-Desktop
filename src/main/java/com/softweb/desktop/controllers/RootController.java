package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.controllers.components.edit.ApplicationEditController;
import com.softweb.desktop.database.entity.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static com.softweb.desktop.StageInitializer.showMainPage;

/**
 * Controller class containing the program window
 */
@Component
public class RootController implements Initializable {

    /**
     * FXML button that opens the login window
     */
    @FXML
    private Button btnLogin;

    /**
     * FXML button that scrolls back through pages sequentially
     */
    @FXML
    private Button btnBack;


    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setVisible(false);
        rebuildButtons(false, false);
    }

    /**
     * The method opens the developer account login page
     */
    public void btnLoginClick() {
        rebuildButtons(true, true);
        StageInitializer.navigate("/layout/PageAuthorizationLayout");
    }

    /**
     * The method performs a pagination back.
     */
    public void btnBackClick() {
        Initializable centerPanelController = StageInitializer.getCenterPanelController();

        if(centerPanelController instanceof ApplicationEditController || centerPanelController instanceof PageUserApplicationsDiagram) {
            rebuildButtons(true, true);
            StageInitializer.navigate("/layout/PageUserApplicationsLayout");
        }
        else {
            rebuildButtons(false, false);
            Authorization.signOut();
            showMainPage();
        }
    }

    /**
     * The method controls the visibility and position of the "Login" and "Back" buttons, depending on the possibility of transition and the presence of an existing authorization
     *
     * @param canGoBack Can go back indicator
     * @param isAuthorized Indicator of already existing authorization in the system
     */
    public void rebuildButtons(boolean canGoBack, boolean isAuthorized) {
        btnBack.setVisible(canGoBack);
        btnLogin.setVisible(!isAuthorized);

        if(canGoBack) {
            VBox.setMargin(btnLogin, new Insets(0,0,-45,0));
        }
        else {
            VBox.setMargin(btnLogin, new Insets(0,0,45,0));
        }
    }
}
