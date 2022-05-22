package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Authorization;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PageAuthorizationController implements Initializable {
    @FXML
    private TextField tbLogin;

    @FXML
    private PasswordField tbMaskedPassword;

    @FXML
    private TextField tbUnmaskedPassword;

    @FXML
    private CheckBox cbShowPassword;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbUnmaskedPassword.setVisible(false);
        synchronizePassword();
    }

    public void cbShowPasswordChecked() {
        if (cbShowPassword.isSelected()) {
            tbUnmaskedPassword.setVisible(true);
            tbMaskedPassword.setVisible(false);
        }
        else {
            tbUnmaskedPassword.setVisible(false);
            tbMaskedPassword.setVisible(true);
        }

    }

    private void synchronizePassword() {
        tbMaskedPassword.textProperty().bindBidirectional(tbUnmaskedPassword.textProperty());
    }

    public void btnLoginClick(ActionEvent event) {
        if(Authorization.authorize(tbLogin.textProperty().getValue(), tbMaskedPassword.textProperty().getValue())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успешно");
            alert.setHeaderText("Здравствуйте, " + Authorization.getCurrentUser()+"!");
            alert.showAndWait();

            StageInitializer.navigate("/layout/PageApplicationsLayout");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неверный логин или пароль!");
            alert.show();
        }
    }
}
