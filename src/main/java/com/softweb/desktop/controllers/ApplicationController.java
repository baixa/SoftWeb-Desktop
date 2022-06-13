package com.softweb.desktop.controllers;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.utils.services.DataService;
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
    public HBox hbImages;

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

    @FXML
    public Label labelWarning;

    @FXML
    public Label labelSystems;

    private Application application;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageInitializer.getRootController().rebuildButtons(true, false);
    }

    public void btnInstallClick() {
        getApplication().download();
        DataService.saveApplication(getApplication());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Установка");
        alert.setHeaderText("Установка завершена!");
        alert.show();
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
            labelSystems.setVisible(true);
        else {
            labelSystems.setVisible(false);
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
    }
}
