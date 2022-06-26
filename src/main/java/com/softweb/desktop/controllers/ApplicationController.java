package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.utils.DataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Класс-контроллер для страницы приложений, содержащую всю информацию об устанавливаемом приложении.
 */
public class ApplicationController implements Initializable {

    /**
     * FXML кнопка установки приложения.
     */
    @FXML
    public Button btnInstall;

    /**
     * FXML узел, содержащий название приложения.
     */
    @FXML
    public Label tbAppName;

    /**
     * FXML узел, содержащий имя разработчика приложения.
     */
    @FXML
    public Label tbDeveloper;

    /**
     * FXML узел, содержащий дополнительную информацию о приложении.
     */
    @FXML
    public AnchorPane details;

    /**
     * FXML узел, содержащий дату последнего обновления приложения.
     */
    @FXML
    public Label tbDateUpdate;

    /**
     * FXML узел, содержащий название лицензии.
     */
    @FXML
    public Label tbLicense;

    /**
     * FXML узел, содержащий список доступных операционных систем.
     */
    @FXML
    public HBox hbOperationSystems;

    /**
     * FXML узел, содержащий список изображений приложения.
     */
    @FXML
    public HBox hbImages;

    /**
     * FXML узел, содержащий заголовок приложения.
     */
    @FXML
    public Label tbShortDescription;

    /**
     * FXML узел, содержащий описание приложения.
     */
    @FXML
    public Text tbFullDescription;

    /**
     * FXML узел, содержащий изображения логотипа Windows.
     */
    @FXML
    public ImageView ivWindows;

    /**
     * FXML узел, содержащий изображение логотипа Linux.
     */
    @FXML
    public ImageView ivLinux;

    /**
     * FXML узел, содержащий изображение логотипа приложения.
     */
    @FXML
    public ImageView ivLogo;

    /**
     * FXML узел, содержащий надпись-предупреждение.
     */
    @FXML
    public Label labelWarning;

    /**
     * FXML узел, содержащий информацию о том, что отсутствуют поддерживаемые системы.
     */
    @FXML
    public Label labelSystemsIsAbsent;

    /**
     * Связанное приложение
     */
    private Application application;


    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageInitializer.getRootController().rebuildButtons(true, false);

    }

    /**
     * Метод выполняет эмуляцию установки приложения
     */
    public void btnInstallClick() {
        getApplication().increaseDownloadsCounter();
        DataService.saveApplication(getApplication());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Установка");
        alert.setHeaderText("Установка завершена!");
        alert.show();
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
        refreshInfo();
    }

    /**
     * Обновление информации на странице.
     */
    private void refreshInfo() {
        this.tbAppName.setText(application.getName());
        this.tbDeveloper.setText(application.getDeveloper().getFullName());
        this.tbShortDescription.setText(application.getShortDescription());
        this.tbFullDescription.setText(application.getDescription());
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        this.tbDateUpdate.setText(dateFormat.format(application.getLastUpdate()));
        if(application.getLicense() != null)
            this.tbLicense.setText(application.getLicense().getCode());
        else
            this.tbLicense.setText("Отсутствует");
        this.ivLogo.setImage(application.getLogo());
        List<ApplicationImage> imageList = new ArrayList<>(application.getImages());
        hbImages.getChildren().clear();
        if(imageList.size() == 0) {
           labelWarning.setVisible(true);
        }
        else {
            labelWarning.setVisible(false);
            imageList.forEach(item -> {
                if(item.getPath() != null) {
                    if(item.getImage() == null)
                        item.setImage(new Image(item.getPath()));

                    ImageView imageView = new ImageView(item.getImage());
                    imageView.setFitWidth(200);
                    imageView.setFitHeight(imageView.getFitWidth() * imageView.getImage().getHeight() / imageView.getImage().getWidth());
                    hbImages.getChildren().add(imageView);
                }
            });
        }

        hbOperationSystems.getChildren().forEach(child -> child.setVisible(false));
        if(application.getInstallers() == null || application.getInstallers().size() == 0)
            labelSystemsIsAbsent.setVisible(true);
        else {
            labelSystemsIsAbsent.setVisible(false);
            for (Installer applicationSystem :
                    application.getInstallers()) {
                if (applicationSystem.getSystem().getName().contains("Windows")) {
                    ivWindows.setVisible(true);
                }
                else if (applicationSystem.getSystem().getName().contains("Debian")) {
                    ivLinux.setVisible(true);
                }
            }
        }

        hbImages.getChildren().forEach(child -> child.hoverProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue) {
                Tooltip imageTip = new Tooltip();
                imageTip.setPrefHeight(((ImageView) child).getImage().getHeight());
                imageTip.setMaxHeight(400);
                imageTip.setPrefWidth(((ImageView) child).getImage().getWidth());
                imageTip.setMaxWidth(imageTip.getMaxHeight() * imageTip.getPrefWidth() / imageTip.getPrefHeight());
                String url = ((ImageView) child).getImage().getUrl().replace('\\', '/');
                imageTip.setStyle("-fx-background-image: url(" + url + ");" +
                        "-fx-background-repeat: stretch; " +
                        "-fx-background-size: stretch; ");

                Tooltip.install(child, imageTip);
            }
        })));
        btnInstall.setDisable(labelSystemsIsAbsent.isVisible());
    }
}
