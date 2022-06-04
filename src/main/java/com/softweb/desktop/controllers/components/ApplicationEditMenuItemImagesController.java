package com.softweb.desktop.controllers.components;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.ApplicationImage;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.ftp.FtpClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.activation.MimetypesFileTypeMap;
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
        getApplication().getImages().add(applicationImage);
        DataService.saveApplicationImage(applicationImage);
        updateApplication();
    }

    @Override
    public void refreshContent() {
        hbImages.getChildren().removeAll(hbImages.getChildren());
        for (ApplicationImage image :
                getApplication().getImages()) {
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
            imageTip.setStyle("-fx-background-image: url(file:///" + url + ");" +
                    "-fx-background-repeat: stretch; " +
                    "-fx-background-size: stretch; ");

            Tooltip.install(imageView, imageTip);
            hbImages.getChildren().add(imageView);
        }
    }

    public void handleDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasFiles()){
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDropped(DragEvent dragEvent) {
        List<File> files = dragEvent.getDragboard().getFiles();

        int limit = 3;
        int freePlaces = limit - getApplication().getImages().size();

        if(files.size() > freePlaces) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Количество фотографий превышает лимит!\nМожно добавить только " + limit + " фото!");
            alert.show();
            return;
        }
        
        List<Image> images = new ArrayList<>();

        for (File file : files) {
            if(!fileIsImage(file)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неверный формат! Файл не является изображением");
                alert.show();
                return;
            }
            else {
                FtpClient ftpClient = new FtpClient("45.153.230.50",21, "newftpuser", "ftp");
                try {
                    String fileExt = Optional.of(file.getName()).filter(f -> f.contains(".")).map(f -> f.substring(file.getName().lastIndexOf(".") + 1)).orElse("");
                    ftpClient.open();
                    InputStream inputStream = new FileInputStream(file);
                    String fileName = java.util.UUID.randomUUID().toString() + "." + fileExt;
                    ftpClient.putFileToPath(inputStream, FtpClient.FTP_DIRECTORY + "images/application_images/" + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName.toString());
                    ftpClient.close();
                    applicationImage = new ApplicationImage();
                    applicationImage.setApplication(getApplication());
                    applicationImage.setPath("images/application_images/" + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName.toString());

                    saveEdits();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Файл недоступен!");
                    alert.show();
                    return;
                }

            }

        }


    }

    private boolean fileIsImage(File file) {
        String mimetype= new MimetypesFileTypeMap().getContentType(file);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }
}
