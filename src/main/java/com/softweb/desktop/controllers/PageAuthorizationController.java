package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.controllers.utils.NodeLimiters;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class containing the authorization page
 */
public class PageAuthorizationController implements Initializable {

    /**
     * An FXML node containing the username.
     */
    @FXML
    private TextField tbLogin;

    /**
     * FXML node containing the hidden password.
     */
    @FXML
    private PasswordField tbMaskedPassword;

    /**
     * An FXML node containing the text version of the password (unhidden).
     */
    @FXML
    private TextField tbUnmaskedPassword;

    /**
     * FXML узел, содержащий чекбокс "Повторить пароль".
     */
    @FXML
    private CheckBox cbShowPassword;

    /**
     * An FXML node containing a link to the registration page.
     */
    @FXML
    private Label labelRegistration;

    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbUnmaskedPassword.setVisible(false);
        labelRegistration.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> StageInitializer.navigate("/layout/PageRegistrationLayout"));
        synchronizePassword();

        NodeLimiters.addTextLimiter(tbLogin, 30);
        NodeLimiters.addTextLimiter(tbMaskedPassword, 30);
        NodeLimiters.addTextLimiter(tbUnmaskedPassword, 30);
    }

    /**
     * The method displays the password if the "Show password" checkbox is enabled, otherwise hides the password
     */
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

    /**
     * The method binds the password fields for the "Show password" function to work
     */
    private void synchronizePassword() {
        tbMaskedPassword.textProperty().bindBidirectional(tbUnmaskedPassword.textProperty());
    }

    /**
     * The method performs authorization of the user based on his entered data
     */
    public void btnLoginClick() {
        if(Authorization.authorize(tbLogin.textProperty().getValue(), tbMaskedPassword.textProperty().getValue())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успешно");
            alert.setHeaderText("Здравствуйте, " + Authorization.getCurrentUser().getFullName()+"!");
            alert.showAndWait();

            StageInitializer.navigate("/layout/PageUserApplicationsLayout");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неверный логин или пароль!");
            alert.show();
        }
    }
}
