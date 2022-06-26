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
 * Класс-контроллер, содержащий окно программы
 */
@Component
public class RootController implements Initializable {

    /**
     * FXML кнопка, открывающая окно входа
     */
    @FXML
    private Button btnLogin;

    /**
     * FXML кнопка, перемещающая последовательно назад по страницам
     */
    @FXML
    private Button btnBack;


    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setVisible(false);
        rebuildButtons(false, false);
    }

    /**
     * Метод открывает страницу входа в аккаунт разработчика
     */
    public void btnLoginClick() {
        rebuildButtons(true, true);
        StageInitializer.navigate("/layout/PageAuthorizationLayout");
    }

    /**
     * Метод выполняет постраничный переход назад.
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
     * Метод управляет видимостью и положением кнопок "Войти" и "Назад" в зависимости от возможности перехода и наличии уже имеющейся авторизации
     * @param canGoBack Индикатор возможности перехода назад
     * @param isAuthorized Индикатор уже имеющейся авторизации в системе
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
