package com.softweb.desktop.controllers.components;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.ApplicationImage;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.ftp.FtpClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public void saveEdits() {
        if(getApplication().getImages() == null)
            getApplication().setImages(new HashSet<>());
        DataService.saveApplicationImage(applicationImage);
        getApplication().getImages().add(applicationImage);
        updateApplication();
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
            hbImages.getChildren().add(imageView);
        }
    }

    private void loadFile(File file) {
        FtpClient ftpClient = new FtpClient("45.67.35.2",21, "softwebftp", "SoftWUser");
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

            saveEdits();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Файл недоступен!");
            alert.show();
        }
    }

    public void fileDialogOpen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"));
        fileChooser.setTitle("Выбрать изображение");
        File file = fileChooser.showOpenDialog(StageInitializer.getStage());

        if(file != null) {
            loadFile(file);
        }
    }
}
