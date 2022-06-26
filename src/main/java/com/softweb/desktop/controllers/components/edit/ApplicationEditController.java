package com.softweb.desktop.controllers.components.edit;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.components.edit.menu.*;
import com.softweb.desktop.database.entity.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Главный контроллер, позволяющий менять информацию о приложении.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public class ApplicationEditController implements Initializable {

    /**
     * FXML узел, содержащий корневой элемент разметки
     */
    @FXML
    public BorderPane rootElement;

    /**
     * FXML узел, содержащий название главной страницы редактирования
     */
    @FXML
    public Label labelMain;

    /**
     * FXML узел, содержащий название страницы редактирования изображений
     */
    @FXML
    public Label labelImages;

    /**
     * FXML узел, содержащий название страницы редактирования установщика
     */
    @FXML
    public Label labelInstaller;

    /**
     * FXML узел, содержащий название страницы редактирования дополнительной информации
     */
    @FXML
    public Label labelAdditional;

    /**
     * FXML узел, содержащий название текущей страницы редактирования
     */
    private Label currentMenuItem;

    /**
     * Связанное приложение
     */
    private Application application;

    /**
     * Текущая страница редактирования.
     */
    private ApplicationEditMenuItem currentItemController;

    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemMain.fxml"));
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentMenuItem = labelMain;
        currentMenuItem.setStyle("-fx-text-fill: #0e8420;");
    }

    /**
     * Получить связанное приложение
     * @return Связанное приложение
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Установить связанное приложение
     * @param application Связанное приложение
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Отобразить текущую страницу.
     */
    public void generatePage() {
        currentItemController.setApplication(application);
        currentItemController.refreshContent();
    }

    /**
     * Открыть страницу с главной информацией.
     */
    public void openMain() {
        if (currentItemController instanceof ApplicationEditMenuItemMainController)
            return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemMain.fxml"));
        generateCenterContent(fxmlLoader, labelMain);
    }

    /**
     * Открыть страницу с изображениями.
     */
    public void openImages() {
        if (currentItemController instanceof ApplicationEditMenuItemImagesController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemImages.fxml"));
        generateCenterContent(fxmlLoader, labelImages);
    }

    /**
     * Сгенерировать страницу редактирования.
     */
    private void generateCenterContent(FXMLLoader fxmlLoader, Label labelImages) {
        try {
            Parent loaded = fxmlLoader.load();
            rootElement.setCenter(loaded);
            currentItemController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        generatePage();

        currentMenuItem.setStyle("-fx-text-fill: #000;");
        currentMenuItem = labelImages;
        currentMenuItem.setStyle("-fx-text-fill: #0e8420;");
    }

    /**
     * Открыть страницу с установщиками.
     */
    public void openInstaller() {
        if (currentItemController instanceof ApplicationEditMenuItemInstallerController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemInstaller.fxml"));
        generateCenterContent(fxmlLoader, labelInstaller);
    }

    /**
     * Открыть страницу с дополнительной информацией.
     */
    public void openAdditional() {
        if (currentItemController instanceof ApplicationEditMenuItemAdditionalController)
            return;

        if (checkingApplicationSaving()) return;

        FXMLLoader fxmlLoader = new FXMLLoader(StageInitializer.class.getResource("/layout/items/ApplicationEditMenuItemAdditional.fxml"));
        generateCenterContent(fxmlLoader, labelAdditional);
    }

    /**
     * Проверяет возможность сохранения изменений приложения
     * @return Индикатор, разрешения на сохранение изменений
     */
    private boolean checkingApplicationSaving() {
        if(getApplication().getId() == null || getApplication().getId() < 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
            alert.setTitle("Внимание");
            alert.setHeaderText("Необходимо название!");
            alert.setContentText("Задайте имя приложению и сохраните изменения прежде чем перейти к этому этапу!");
            alert.show();
            return true;
        }
        return false;
    }
}
