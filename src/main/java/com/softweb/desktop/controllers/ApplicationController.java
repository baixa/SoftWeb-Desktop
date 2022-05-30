package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationController implements Initializable {

    @FXML
    public Button btnInstall;

    @FXML
    public Label tbAppName;

    @FXML
    public Label tbDeveloper;

    @FXML
    public AnchorPane details;

    @FXML
    public Label tbDateUpdate;

    @FXML
    public Label tbLicense;

    @FXML
    public HBox hbOperationSystems;

    @FXML
    public ScrollPane spImages;

    @FXML
    public AnchorPane apImages;

    @FXML
    public HBox hbImages;

    @FXML
    public ImageView ivFirstImage;

    @FXML
    public ImageView ivSecondImage;

    @FXML
    public ImageView ivThirdImage;

    @FXML
    public Label tbShortDescription;

    @FXML
    public Text tbFullDescription;

    @FXML
    public ImageView ivWindows;

    @FXML
    public ImageView ivLinux;

    @FXML
    public ImageView ivLogo;

    private Application application;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbImages.getChildren().forEach(child -> child.hoverProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue) {
                Tooltip imageTip = new Tooltip();
                imageTip.setPrefHeight(((ImageView) child).getImage().getHeight());
                imageTip.setMaxHeight(400);
                imageTip.setPrefWidth(((ImageView) child).getImage().getWidth());
                imageTip.setMaxWidth(imageTip.getMaxHeight() * imageTip.getPrefWidth() / imageTip.getPrefHeight());
                imageTip.setStyle("-fx-background-image: url(" + ((ImageView) child).getImage().getUrl() + ");" +
                        "-fx-background-repeat: stretch; " +
                        "-fx-background-size: stretch; ");

                Tooltip.install(child, imageTip);
            }
        })));
        StageInitializer.getRootController().rebuildButtons(true, false);
    }

    public void btnInstallClick(ActionEvent event) {
        System.out.println(1);
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
        refreshInfo();
    }

    private void refreshInfo() {
        this.tbAppName.setText(application.getName());
        this.tbDeveloper.setText(application.getDeveloper().getFullName());
        this.tbShortDescription.setText(application.getShortDescription());
        this.tbFullDescription.setText(application.getDescription());
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        this.tbDateUpdate.setText(dateFormat.format(application.getLastUpdate()));
        this.tbLicense.setText(application.getLicense());
        this.ivLogo.setImage(new Image(application.getLogoPath()));
        List<ApplicationImage> imageList = new ArrayList<>(application.getImages());
        ivFirstImage.setImage(new Image(imageList.get(0).getPath()));
        ivSecondImage.setImage(new Image(imageList.get(1).getPath()));
        ivThirdImage.setImage(new Image(imageList.get(2).getPath()));

        hbOperationSystems.getChildren().forEach(child -> child.setVisible(false));
        for (ApplicationsSystems applicationSystem :
                application.getApplicationsSystems()) {
            if (applicationSystem.getSystem().getName().equals("Windows 10")) {
                ivWindows.setVisible(true);
            }
            else if (applicationSystem.getSystem().getName().equals("Debian/Ubuntu")) {
                ivLinux.setVisible(true);
            }
        }
    }
}
