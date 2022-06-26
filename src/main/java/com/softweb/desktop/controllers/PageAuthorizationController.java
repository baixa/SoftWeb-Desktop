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
 * Класс-контроллер, содержащий страницу авторизации
 */
public class PageAuthorizationController implements Initializable {

    /**
     * FXML узел, содержащий имя пользователя.
     */
    @FXML
    private TextField tbLogin;

    /**
     * FXML узел, содержащий скрытый пароль.
     */
    @FXML
    private PasswordField tbMaskedPassword;

    /**
     * FXML узел, содержащий текствую версию пароля (нескрытую).
     */
    @FXML
    private TextField tbUnmaskedPassword;

    /**
     * FXML узел, содержащий чекбокс "Повторить пароль".
     */
    @FXML
    private CheckBox cbShowPassword;

    /**
     * FXML узел, содержащий ссылку на страницу регистрации.
     */
    @FXML
    private Label labelRegistration;

    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
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
     * Метод отображает пароль, если включен флажок "Показать пароль", в ином случае скрывает пароль
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
     * Метод связывает поля пароля для работы функции "Показать пароль"
     */
    private void synchronizePassword() {
        tbMaskedPassword.textProperty().bindBidirectional(tbUnmaskedPassword.textProperty());
    }

    /**
     * Метод выполняет авторизацию пользователя на основе его введенных данных
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
