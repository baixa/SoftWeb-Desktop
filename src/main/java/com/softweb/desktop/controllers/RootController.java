package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Authorization;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static com.softweb.desktop.StageInitializer.showDefaultContent;

@Component
public class RootController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnBack;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setVisible(false);
        rebuildButtons(false, false);
    }

    public void btnLoginClick(ActionEvent event) {
        rebuildButtons(true, true);
        showAuthorizationForm();
    }

    private void showAuthorizationForm() {
        StageInitializer.navigate("/layout/PageAuthorizationLayout");
    }

    public void btnBackClick(ActionEvent event) {
        rebuildButtons(false, false);
        Authorization.unauthorize();
        showDefaultContent();
    }



    private void rebuildButtons(boolean canGoBack, boolean isAuthorized) {
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
