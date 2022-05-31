package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entity.ApplicationImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationEditMenuItemImagesController extends ApplicationEditMenuItem implements Initializable {

    @FXML
    private HBox hbImages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void saveEdits() {

    }

    @Override
    public void refreshContent() {
        hbImages.getChildren().removeAll(hbImages.getChildren());
        for (ApplicationImage image :
                getApplication().getImages()) {
            Image appImage = new Image(image.getPath());
            ImageView imageView = new ImageView(appImage);
            imageView.setFitWidth(120);
            imageView.setFitHeight(imageView.getFitWidth() * appImage.getHeight() / appImage.getWidth());
            Tooltip imageTip = new Tooltip();
            imageTip.setPrefHeight(imageView.getImage().getHeight());
            imageTip.setMaxHeight(400);
            imageTip.setPrefWidth(imageView.getImage().getWidth());
            imageTip.setMaxWidth(imageTip.getMaxHeight() * imageTip.getPrefWidth() / imageTip.getPrefHeight());
            imageTip.setStyle("-fx-background-image: url(" + imageView.getImage().getUrl() + ");" +
                    "-fx-background-repeat: stretch; " +
                    "-fx-background-size: stretch; ");

            Tooltip.install(imageView, imageTip);
            hbImages.getChildren().add(imageView);
        }
    }
}
