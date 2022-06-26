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
 * Класс-контроллер, отображающий страницу регистрации
 */
public class PageRegistrationController  implements Initializable {

    /**
     * FXML узел, содержащий логин пользователя.
     */
    @FXML
    private TextField tbLogin;

    /**
     * FXML узел, содержащий имя пользователя.
     */
    @FXML
    private TextField tbName;

    /**
     * FXML узел, содержащий скрытый пароль пользователя.
     */
    @FXML
    private PasswordField tbMaskedPassword;

    /**
     * FXML узел, содержащий текстовую версию пароля (нескрытую).
     */
    @FXML
    private TextField tbUnmaskedPassword;

    /**
     * FXML узел, содержащий скрытый подтвержденный пароль.
     */
    @FXML
    private PasswordField tbMaskedPasswordConfirm;

    /**
     * FXML узел, содержащий текстовую версию подтвержденного пароля (нескрытую).
     */
    @FXML
    private TextField tbUnmaskedPasswordConfirm;

    /**
     * FXML узел, содержащий ссылку на страницу входа.
     */
    @FXML
    private Label labelLogin;

    /**
     * FXML узел, содержащий чекбокс "Повторить пароль".
     */
    @FXML
    private CheckBox cbShowPassword;

    /**
     * Кэш базы данных для выполнения CRUD операций и сохранения информации.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
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
     * Метод отображает пароль, если включен флажок "Показать пароль", в ином случае скрывает пароль
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
     * Метод связывает поля пароля для работы функции "Показать пароль"
     */
    private void synchronizePassword() {
        tbMaskedPassword.textProperty().bindBidirectional(tbUnmaskedPassword.textProperty());
        tbMaskedPasswordConfirm.textProperty().bindBidirectional(tbUnmaskedPasswordConfirm.textProperty());
    }

    /**
     * Метод выполняет регистрацию пользователя на основе введенных данных.
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
