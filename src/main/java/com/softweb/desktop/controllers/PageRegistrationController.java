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

/**
 * Controller class that displays the registration page
 */
public class PageRegistrationController  implements Initializable {

    /**
     * An FXML node containing the user's login.
     */
    @FXML
    private TextField tbLogin;

    /**
     * An FXML node containing the username.
     */
    @FXML
    private TextField tbName;

    /**
     * An FXML node containing the user's hidden password.
     */
    @FXML
    private PasswordField tbMaskedPassword;

    /**
     * FXML node containing the text version of the password (unhidden).
     */
    @FXML
    private TextField tbUnmaskedPassword;

    /**
     * An FXML node containing a hidden verified password.
     */
    @FXML
    private PasswordField tbMaskedPasswordConfirm;

    /**
     * An FXML node containing the text version of the verified password (unhidden).
     */
    @FXML
    private TextField tbUnmaskedPasswordConfirm;

    /**
     * An FXML node containing a link to the login page.
     */
    @FXML
    private Label labelLogin;

    /**
     * FXML node containing the "Repeat password" checkbox.
     */
    @FXML
    private CheckBox cbShowPassword;

    /**
     * Database cache to perform CRUD operations and save information.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
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

    /**
     * The method displays the password if the "Show password" checkbox is enabled, otherwise hides the password
     */
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

    /**
     * The method binds the password fields for the "Show password" function to work
     */
    private void synchronizePassword() {
        tbMaskedPassword.textProperty().bindBidirectional(tbUnmaskedPassword.textProperty());
        tbMaskedPasswordConfirm.textProperty().bindBidirectional(tbUnmaskedPasswordConfirm.textProperty());
    }

    /**
     * The method performs user registration based on the entered data.
     */
    public void btnRegisterClick() {
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
