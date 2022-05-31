package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entity.ApplicationsSystems;
import com.softweb.desktop.database.entity.OperationSystem;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ivWindows.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
               if(!windowsSelected) {
                   ApplicationsSystems applicationsSystems = getApplication().getApplicationsSystems().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Windows")).findFirst().orElse(null);
                   if(applicationsSystems != null)
                   {
                       labelWarning.setVisible(false);
                       try {
                           double size = getFileSize(new URL(applicationsSystems.getInstallerPath()));
                           size = (size / 1024) / 1024;
                           DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                           tbSize.textProperty().setValue(decimalFormat.format(size) + " Мб");
                       } catch (IOException e) {
                           tbSize.textProperty().setValue("Не установлено");
                       }
                       tbOS.textProperty().setValue(applicationsSystems.getSystem().getName());
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
        });

        ivLinux.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(windowsSelected) {
                ApplicationsSystems applicationsSystems = getApplication().getApplicationsSystems().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Debian")).findFirst().orElse(null);
                if(applicationsSystems != null)
                {
                    labelWarning.setVisible(false);
                    try {
                        double size = getFileSize(new URL(applicationsSystems.getInstallerPath()));
                        size = (size / 1024) / 1024;
                        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                        tbSize.textProperty().setValue(decimalFormat.format(size) + " Мб");
                    } catch (IOException e) {
                        tbSize.textProperty().setValue("Не установлено");
                    }
                    tbOS.textProperty().setValue(applicationsSystems.getSystem().getName());
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
            }
        });
    }

    @Override
    public void refreshContent() {
        ApplicationsSystems applicationsSystems = getApplication().getApplicationsSystems().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Windows")).findFirst().orElse(null);
        if(applicationsSystems != null)
        {
            labelWarning.setVisible(false);
            try {
                double size = getFileSize(new URL(applicationsSystems.getInstallerPath()));
                size = (size / 1024) / 1024;
                DecimalFormat decimalFormat = new DecimalFormat("##0.00");
                tbSize.textProperty().setValue(decimalFormat.format(size) + " Мб");
            } catch (IOException e) {
                tbSize.textProperty().setValue("Не установлено");
            }
            tbOS.textProperty().setValue(applicationsSystems.getSystem().getName());
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

    private static int getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }

    @Override
    public void saveEdits() {

    }
}
