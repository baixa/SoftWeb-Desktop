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
 * The controller class for the application page containing all the information about the application being installed.
 */
public class ApplicationController implements Initializable {

    /**
     * FXML app install button.
     */
    @FXML
    public Button btnInstall;

    /**
     * FXML node containing the name of the application.
     */
    @FXML
    public Label tbAppName;

    /**
     * An FXML node containing the name of the application developer.
     */
    @FXML
    public Label tbDeveloper;

    /**
     * FXML node containing additional information about the application.
     */
    @FXML
    public AnchorPane details;

    /**
     * FXML node containing the date the application was last updated.
     */
    @FXML
    public Label tbDateUpdate;

    /**
     * FXML node containing license name.
     */
    @FXML
    public Label tbLicense;

    /**
     * FXML node containing a list of available operating systems.
     */
    @FXML
    public HBox hbOperationSystems;

    /**
     * FXML node containing a list of application images.
     */
    @FXML
    public HBox hbImages;

    /**
     * FXML node containing the application title.
     */
    @FXML
    public Label tbShortDescription;

    /**
     * An FXML node containing a description of the application.
     */
    @FXML
    public Text tbFullDescription;

    /**
     * FXML node containing Windows logo images.
     */
    @FXML
    public ImageView ivWindows;

    /**
     * FXML node containing the Linux logo image.
     */
    @FXML
    public ImageView ivLinux;

    /**
     * FXML node containing the application logo image.
     */
    @FXML
    public ImageView ivLogo;

    /**
     *  FXML node containing warning label.
     */
    @FXML
    public Label labelWarning;

    /**
     *  FXML node containing information that there are no supported systems.
     */
    @FXML
    public Label labelSystemsIsAbsent;

    /**
     * Related application
     */
    private Application application;


    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageInitializer.getRootController().rebuildButtons(true, false);

    }

    /**
     * The method emulates the installation of the application
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
     * Get a related app
     * @return Related application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Set a related app
     * @param application Related application
     */
    public void setApplication(Application application) {
        this.application = application;
        refreshInfo();
    }

    /**
     * Update information on the page.
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
