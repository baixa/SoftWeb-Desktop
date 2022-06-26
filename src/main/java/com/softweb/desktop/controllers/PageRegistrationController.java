package com.softweb.desktop.controllers;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Registration;
import com.softweb.desktop.controllers.utils.NodeLimiters;
import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.DBCache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
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

    private static DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbUnmaskedPassword.setVisible(false);
        tbUnmaskedPasswordConfirm.setVisible(false);
        labelLogin.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> StageInitializer.navigate("/layout/PageAuthorizationLayout"));
        synchronizePassword();
        NodeLimiters.addTextLimiter(tbLogin, 30);
        NodeLimiters.addTextLimiter(tbName, 100);
        NodeLimiters.addTextLimiter(tbMaskedPassword, 30);
        NodeLimiters.addTextLimiter(tbUnmaskedPassword, 30);
        NodeLimiters.addTextLimiter(tbMaskedPasswordConfirm, 30);
        NodeLimiters.addTextLimiter(tbUnmaskedPasswordConfirm, 30);
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

        String username = tbLogin.textProperty().getValue();
        String password = tbMaskedPassword.textProperty().getValue();
        String fullName = tbName.textProperty().getValue();

        List<Developer> developers = dbCache.getDevelopers();
        Developer existedDeveloper = developers.stream()
                .filter(developer -> developer.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if(existedDeveloper != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пользователь с таким логином уже имеется в системе!");
            alert.show();
        }
        else {
            Registration.register(username, fullName, password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успешно");
            alert.setHeaderText("Пользователь " + username + " зарегистрирован!");
            alert.show();

            StageInitializer.navigate("/layout/PageUserApplicationsLayout");
        }
    }
}
