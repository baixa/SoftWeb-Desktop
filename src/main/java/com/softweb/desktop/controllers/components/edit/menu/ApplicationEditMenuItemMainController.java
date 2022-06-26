package com.softweb.desktop.controllers.components.edit.menu;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.utils.NodeLimiters;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.utils.ftp.FtpClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Контроллер, позволяющий менять Основную информацию о приложении.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public class ApplicationEditMenuItemMainController extends ApplicationEditMenuItem implements Initializable{
    /**
     * FXML узел, содержащий название приложения
     */
    @FXML
    public TextField tbAppName;

    /**
     * FXML узел, содержащий заголовок приложения
     */
    @FXML
    public TextField tbShortDescription;

    /**
     * FXML узел, содержащий описание приложения
     */
    @FXML
    public TextArea tbDescription;

    /**
     * FXML узел, содержащий изображение логотипа приложения
     */
    @FXML
    public ImageView ivLogo;

    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeLimiters.addTextLimiter(tbAppName, 30);
        NodeLimiters.addTextLimiter(tbShortDescription, 50);
        NodeLimiters.addTextLimiter(tbDescription, 1500);
    }

    /**
     * Метод сохраняет изменения приложения в БД.
     */
    public void saveEdits() {
        getApplication().setName(tbAppName.textProperty().getValue());
        getApplication().setShortDescription(tbShortDescription.textProperty().getValue());
        getApplication().setDescription(tbDescription.textProperty().getValue());
        updateApplication();
        refreshContent();
    }

    /**
     * Обновление информации на странице.
     */
    @Override
    public void refreshContent() {
        this.tbAppName.textProperty().setValue(getApplication().getName());
        this.tbShortDescription.textProperty().setValue(getApplication().getShortDescription());
        this.tbDescription.textProperty().setValue(getApplication().getDescription());
        this.ivLogo.setImage(getApplication().getLogo());
    }

    /**
     * Метод загружает выбранный файл на FTP сервер.
     * @param file Выбранный файл
     */
    private void loadFile(File file) {
        FtpClient ftpClient = JavaFXMain.getApplicationContext().getBean(FtpClient.class);
        try {
            String fileExt = Optional.of(file.getName()).filter(f -> f.contains(".")).map(f -> f.substring(file.getName().lastIndexOf("."))).orElse("");
            ftpClient.open();
            InputStream inputStream = new FileInputStream(file);
            String fileName = java.util.UUID.randomUUID().toString() + fileExt;
            ftpClient.putFileToPath(inputStream, FtpClient.LOGO_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
            ftpClient.close();
            Application application = getApplication();
            if (application.getLogoPath() != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Внимание");
                alert.setHeaderText("Выбранное изображение заменит текущее! Продолжить?");
                AtomicBoolean changeExisted = new AtomicBoolean(false);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        changeExisted.set(true);
                    }
                });
                if (!changeExisted.get()) {
                    return;
                }
            }
            application.setLogoPath(FtpClient.WEB_PATH + FtpClient.LOGO_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
            saveEdits();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Файл недоступен!");
            alert.show();
        }
    }

    /**
     * Метод открывает диалоговое окно выбора изображения.
     * Поддерживаемые форматы: PNG, JPEG, JPG.
     */
    public void fileDialogOpen() {
        if(getApplication().getId() == null || getApplication().getId() < 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
            alert.setTitle("Внимание");
            alert.setHeaderText("Необходимо название!");
            alert.setContentText("Задайте имя приложению и сохраните изменения прежде чем задать логотип!");
            alert.show();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"));
        fileChooser.setTitle("Выбрать изображение");
        File file = fileChooser.showOpenDialog(StageInitializer.getStage());

        if(file != null) {
            loadFile(file);
            saveEdits();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setTitle("Результат");
            alert.setHeaderText("Готово!");
            alert.show();
        }

    }

    /**
     * Сохранение изменений приложения.
     */
    public void save() {
        saveEdits();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Результат");
        alert.setHeaderText("Готово!");
        alert.show();
    }
}
