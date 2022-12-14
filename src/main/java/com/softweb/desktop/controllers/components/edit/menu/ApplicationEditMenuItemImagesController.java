package com.softweb.desktop.controllers.components.edit.menu;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.ApplicationImage;
import com.softweb.desktop.database.utils.DataService;
import com.softweb.desktop.utils.ftp.FtpClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * A controller that allows you to interact with the application's images.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationEditMenuItemImagesController extends ApplicationEditMenuItem implements Initializable {

    /**
     * An FXML node containing a list of application images.
     */
    @FXML
    private HBox hbImages;

    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Update information on the page.
     */
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


    /**
     * The method uploads files to the server via FTP and saves the image data to the database
     *
     * @param file Download file
     */
    private void loadFile(File file) {
        FtpClient ftpClient = JavaFXMain.getApplicationContext().getBean(FtpClient.class);
        try {
            String fileExt = Optional.of(file.getName()).filter(f -> f.contains(".")).map(f -> f.substring(file.getName().lastIndexOf(".") + 1)).orElse("");
            ftpClient.open();
            InputStream inputStream = new FileInputStream(file);
            String fileName = java.util.UUID.randomUUID().toString() + "." + fileExt;
            ftpClient.putFileToPath(inputStream, FtpClient.IMAGE_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
            ftpClient.close();
            ApplicationImage applicationImage = new ApplicationImage();
            applicationImage.setApplication(getApplication());
            applicationImage.setPath(FtpClient.WEB_PATH + FtpClient.IMAGE_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);


            if(getApplication().getImages() == null)
                getApplication().setImages(new HashSet<>());
            addApplicationImage(applicationImage);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("????????????");
            alert.setHeaderText("?????????????? ???????????????? ?????????? ???? ??????????????!\n?????????????????? ????????????????-???????????????????? ?? ???????????????????? ?????? ??????!");
            alert.show();
        }
    }

    /**
     * The method opens an image selection dialog.
     * Supported formats: PNG, JPEG, JPG.
     */
    public void openFileDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"));
        fileChooser.setTitle("?????????????? ??????????????????????");
        File file = fileChooser.showOpenDialog(StageInitializer.getStage());

        if(file != null) {
            loadFile(file);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setTitle("??????????????????");
            alert.setHeaderText("????????????!");
            alert.show();
        }

    }

    /**
     * The method deletes the image selected by the user after confirming it.
     *
     * @param imageView The image to remove.
     */
    public void removeImage(ImageView imageView) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "???? ??????????????, ?????? ???????????? ?????????????? ???????????????????????", ButtonType.YES, ButtonType.NO);
        alert.setTitle("????????????????!");
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
                infoAlert.setTitle("??????????????????");
                infoAlert.setHeaderText("????????????!");
                infoAlert.show();
            }
        });

    }


    /**
     * The method adds an image to the system.
     *
     * @param addableImage Image to add
     */
    public void addApplicationImage(ApplicationImage addableImage) {
        this.application.setLastUpdate(new Date());
        getApplication().getImages().add(addableImage);
        DataService.saveApplication(getApplication());
        DataService.saveApplicationImage(addableImage);
        dbCache.getApplications().stream()
                .filter(item -> item.getId().equals(getApplication().getId()))
                .findFirst().ifPresent(this::setApplication);
        dbCache.clear();
        refreshContent();
    }


    /**
     * The method removes the selected ApplicationImage object from the database.
     *
     * @param removableImage The object to be removed.
     */
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
