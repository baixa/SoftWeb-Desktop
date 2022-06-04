package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entity.ApplicationsSystems;
import com.softweb.desktop.database.utils.services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ApplicationEditMenuItemInstallerController extends ApplicationEditMenuItem implements Initializable {

    @FXML
    private Label labelWarning;

    @FXML
    private ImageView ivWindows;

    @FXML
    private ImageView ivLinux;

    @FXML
    private Label tbSize;

    @FXML
    private Label tbOS;

    @FXML
    private Label tbCommand;

    private boolean windowsSelected = false;

    private ApplicationsSystems applicationsSystem;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ivLinux.setImage(new Image(String.valueOf(getClass().getResource("/images/linux-gray.png").toURI())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ivWindows.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
               if(!windowsSelected) {
                   fillWindowsInfo();
                   try {
                       ivWindows.setImage(new Image(String.valueOf(getClass().getResource("/images/windows.png").toURI())));
                       ivLinux.setImage(new Image(String.valueOf(getClass().getResource("/images/linux-gray.png").toURI())));
                   } catch (URISyntaxException e) {
                       e.printStackTrace();
                   }
               }
        });

        ivLinux.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(windowsSelected) {
                ApplicationsSystems applicationsSystems = getApplication().getApplicationsSystems().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Debian")).findFirst().orElse(null);
                if(applicationsSystems != null)
                {
                    fillInstallerInfo(applicationsSystems);
                    tbCommand.setStyle("-fx-text-fill: #66666699;");
                    tbCommand.textProperty().setValue("Не требуется");

                }
                else {
                    tbSize.textProperty().setValue("Не установлено");
                    tbOS.textProperty().setValue("Не установлено");
                    tbCommand.textProperty().setValue("Не установлено");
                    labelWarning.setVisible(true);
                }
                windowsSelected = false;
                try {
                    ivWindows.setImage(new Image(String.valueOf(getClass().getResource("/images/windows-gray.png").toURI())));
                    ivLinux.setImage(new Image(String.valueOf(getClass().getResource("/images/linux.png").toURI())));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        ivWindows.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    ivWindows.setImage(new Image(String.valueOf(getClass().getResource("/images/windows-hover.png").toURI())));

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if(windowsSelected)
                        ivWindows.setImage(new Image(String.valueOf(getClass().getResource("/images/windows.png").toURI())));
                    else
                        ivWindows.setImage(new Image(String.valueOf(getClass().getResource("/images/windows-gray.png").toURI())));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        ivLinux.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    ivLinux.setImage(new Image(String.valueOf(getClass().getResource("/images/linux-hover.png").toURI())));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if(!windowsSelected)
                        ivLinux.setImage(new Image(String.valueOf(getClass().getResource("/images/linux.png").toURI())));
                    else
                        ivLinux.setImage(new Image(String.valueOf(getClass().getResource("/images/linux-gray.png").toURI())));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        ivWindows.setCursor(Cursor.HAND);
        ivLinux.setCursor(Cursor.HAND);
    }

    @Override
    public void refreshContent() {
        fillWindowsInfo();
    }

    private void fillWindowsInfo() {
        ApplicationsSystems applicationsSystems = getApplication().getApplicationsSystems().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Windows")).findFirst().orElse(null);
        if(applicationsSystems != null)
        {
            fillInstallerInfo(applicationsSystems);
            tbCommand.setStyle("-fx-text-fill: #075d5b;");
            tbCommand.textProperty().setValue("$installerName \\SILENTMODE");
        }
        else {
            tbSize.textProperty().setValue("Не установлено");
            tbOS.textProperty().setValue("Не установлено");
            tbCommand.textProperty().setValue("Не установлено");
            labelWarning.setVisible(true);
        }
        windowsSelected = true;
    }

    private void fillInstallerInfo(ApplicationsSystems applicationsSystems) {
        labelWarning.setVisible(false);
        double size = applicationsSystems.getSize();
        size = (size / 1024) / 1024;
        DecimalFormat decimalFormat = new DecimalFormat("###0.00");
        tbSize.textProperty().setValue(decimalFormat.format(size) + " Мб");
        tbOS.textProperty().setValue(applicationsSystems.getSystem().getName());
    }

    @Override
    public void saveEdits() {
        DataService.saveApplicationSystem(applicationsSystem);
        updateApplication();
    }
}
