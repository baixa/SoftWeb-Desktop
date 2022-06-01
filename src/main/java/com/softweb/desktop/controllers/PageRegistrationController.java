package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Registration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PageRegistrationController  implements Initializable {
    @FXML
    private TextField tbLogin;

    @FXML
    private TextField tbName;

    @FXML
    private PasswordField tbMaskedPassword;

    @FXML
    private TextField tbUnmaskedPassword;

    @FXML
    private PasswordField tbMaskedPasswordConfirm;

    @FXML
    private TextField tbUnmaskedPasswordConfirm;

    @FXML
    private Label labelLogin;

    @FXML
    private CheckBox cbShowPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbUnmaskedPassword.setVisible(false);
        tbUnmaskedPasswordConfirm.setVisible(false);
        labelLogin.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> StageInitializer.navigate("/layout/PageAuthorizationLayout"));
        synchronizePassword();
    }

    public void cbShowPasswordChecked() {
        if (cbShowPassword.isSelected()) {
            tbUnmaskedPassword.setVisible(true);
            tbMaskedPassword.setVisible(false);
            tbUnmaskedPasswordConfirm.setVisible(true);
            tbMaskedPasswordConfirm.setVisible(false);
        }
        else {
            tbUnmaskedPassword.setVisible(false);
            tbMaskedPassword.setVisible(true);
            tbUnmaskedPasswordConfirm.setVisible(false);
            tbMaskedPasswordConfirm.setVisible(true);
        }

    }

    private void synchronizePassword() {
        tbMaskedPassword.textProperty().bindBidirectional(tbUnmaskedPassword.textProperty());
        tbMaskedPasswordConfirm.textProperty().bindBidirectional(tbUnmaskedPasswordConfirm.textProperty());
    }

    public void btnRegisterClick(ActionEvent event) {
        if(!tbMaskedPassword.textProperty().getValue().equals(tbMaskedPasswordConfirm.textProperty().getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пароли не совпадают!");
            alert.show();
            return;
        }

        try {
            String username = tbLogin.textProperty().getValue();
            String password = tbMaskedPassword.textProperty().getValue();
            String fullName = tbName.textProperty().getValue();
            Registration.register(username, fullName, password);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }
    }
}
