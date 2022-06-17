package com.softweb.desktop.controllers.components.menu;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.ApplicationImage;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.ftp.FtpClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class ApplicationEditMenuItemImagesController extends ApplicationEditMenuItem implements Initializable {

    @FXML
    public AnchorPane apDragAndDrop;

    @FXML
    private HBox hbImages;

    private ApplicationImage applicationImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void refreshContent() {
        hbImages.getChildren().removeAll(hbImages.getChildren());
        for (ApplicationImage image :
                getApplication().getImages()) {
            if(image.getPath() == null)
                continue;

            if(image.getImage() == null)
                image.setImage(new Image(image.getPath()));

            Image appImage = image.getImage();
            ImageView imageView = new ImageView(appImage);
            imageView.setFitWidth(120);
            imageView.setFitHeight(imageView.getFitWidth() * appImage.getHeight() / appImage.getWidth());
            Tooltip imageTip = new Tooltip();
            imageTip.setPrefHeight(imageView.getImage().getHeight());
            imageTip.setMaxHeight(400);
            imageTip.setPrefWidth(imageView.getImage().getWidth());
            imageTip.setMaxWidth(imageTip.getMaxHeight() * imageTip.getPrefWidth() / imageTip.getPrefHeight());
            String url = imageView.getImage().getUrl().replace('\\', '/');
            imageTip.setStyle("-fx-background-image: url(" + url + ");" +
                    "-fx-background-repeat: stretch; " +
                    "-fx-background-size: stretch; ");

            Tooltip.install(imageView, imageTip);
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> removeImage(imageView));

            hbImages.getChildren().add(imageView);
        }
    }

    private void loadFile(File file) {
        FtpClient ftpClient = JavaFXMain.getApplicationContext().getBean(FtpClient.class);
        try {
            String fileExt = Optional.of(file.getName()).filter(f -> f.contains(".")).map(f -> f.substring(file.getName().lastIndexOf(".") + 1)).orElse("");
            ftpClient.open();
            InputStream inputStream = new FileInputStream(file);
            String fileName = java.util.UUID.randomUUID().toString() + "." + fileExt;
            ftpClient.putFileToPath(inputStream, FtpClient.IMAGE_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
            ftpClient.close();
            applicationImage = new ApplicationImage();
            applicationImage.setApplication(getApplication());
            applicationImage.setPath(FtpClient.WEB_PATH + FtpClient.IMAGE_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);


            if(getApplication().getImages() == null)
                getApplication().setImages(new HashSet<>());
            addApplicationImage(applicationImage);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Попытка передачи файла не удалась!\nПроверьте интернет-соединение и попробуйте еще раз!");
            alert.show();
        }
    }

    public void fileDialogOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"));
        fileChooser.setTitle("Выбрать изображение");
        File file = fileChooser.showOpenDialog(StageInitializer.getStage());

        if(file != null) {
            loadFile(file);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setTitle("Результат");
            alert.setHeaderText("Готово!");
            alert.show();
        }

    }

    public void removeImage(ImageView imageView) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Вы уверены, что хотите удалить изображение?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Внимание!");
        alert.showAndWait().ifPresent(response -> {
            Image image = imageView.getImage();
            String path = image.getUrl();
            ApplicationImage removableImage  = getApplication()
                    .getImages()
                    .stream()
                    .filter(item -> item.getPath().equals(path))
                    .findFirst()
                    .orElse(null);
            if(removableImage != null) {
                removeApplicationImage(removableImage);
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                infoAlert.setTitle("Результат");
                infoAlert.setHeaderText("Готово!");
                infoAlert.show();
            }
        });

    }

    public void addApplicationImage(ApplicationImage updatableImage) {
        this.application.setLastUpdate(new Date());
        getApplication().getImages().add(updatableImage);
        DataService.saveApplication(getApplication());
        DataService.saveApplicationImage(updatableImage);
        dbCache.getApplications().stream()
                .filter(item -> item.getId().equals(getApplication().getId()))
                .findFirst().ifPresent(this::setApplication);
        dbCache.clear();
        refreshContent();
    }

    public void removeApplicationImage(ApplicationImage removableImage) {
        this.application.setLastUpdate(new Date());
        getApplication().getImages().remove(removableImage);
        DataService.deleteApplicationImage(removableImage);
        dbCache.getApplications().stream()
                .filter(item -> item.getId().equals(getApplication().getId()))
                .findFirst().ifPresent(this::setApplication);
        dbCache.clear();
        refreshContent();
    }
}
