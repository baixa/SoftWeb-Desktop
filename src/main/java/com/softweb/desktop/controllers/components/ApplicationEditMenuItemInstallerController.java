package com.softweb.desktop.controllers.components;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.Installer;
import com.softweb.desktop.database.entity.OperatingSystem;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.ftp.FtpClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private Installer applicationsSystem;


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
                Installer installer = getApplication().getInstallers().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Debian")).findFirst().orElse(null);
                if(installer != null)
                {
                    fillInstallerInfo(installer);
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
        Installer installer = getApplication().getInstallers().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Windows")).findFirst().orElse(null);
        if(installer != null)
        {
            fillInstallerInfo(installer);
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

    private void fillInstallerInfo(Installer installer) {
        labelWarning.setVisible(false);
        double size = installer.getSize();
        size = (size / 1024) / 1024;
        DecimalFormat decimalFormat = new DecimalFormat("###0.00");
        tbSize.textProperty().setValue(decimalFormat.format(size) + " Мб");
        tbOS.textProperty().setValue(installer.getSystem().getName());
    }

    @Override
    public void saveEdits() {
        getApplication().getInstallers().add(applicationsSystem);
        applicationsSystem.getSystem().getApplicationsSystems().add(applicationsSystem);
        DataService.saveApplicationSystem(applicationsSystem);
        DataService.saveOperationSystem(applicationsSystem.getSystem());
        updateApplication();
    }

    private void loadFile(File file) {
        FtpClient ftpClient = new FtpClient("45.67.35.2",21, "softwebftp", "SoftWUser");
        try {
            String fileExt = Optional.of(file.getName()).filter(f -> f.contains(".")).map(f -> f.substring(file.getName().lastIndexOf("."))).orElse("");
            ftpClient.open();
            InputStream inputStream = new FileInputStream(file);
            String fileName = java.util.UUID.randomUUID().toString() + fileExt;
            ftpClient.putFileToPath(inputStream, FtpClient.INSTALLER_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
            ftpClient.close();
            OperatingSystem system;
            if (fileExt.equals(".deb")) {
                system = DBCache.getCache().getSystems().stream().filter(item -> item.getName().contains("Debian")).findFirst().orElse(null);
            }
            else {
                system = DBCache.getCache().getSystems().stream().filter(item -> item.getName().contains("Windows")).findFirst().orElse(null);
            }
            List<Installer> applicationsSystems = new ArrayList<>(getApplication().getInstallers());
            Installer existedItem = applicationsSystems.stream()
                    .filter(item -> item.getApplication().getId().equals(getApplication().getId()))
                    .filter(item -> item.getSystem().getId().equals(system.getId()))
                    .findFirst()
                    .orElse(null);
            if (existedItem == null) {
                applicationsSystem = new Installer();
                applicationsSystem.setApplication(getApplication());
                applicationsSystem.setApplication(getApplication());
                applicationsSystem.setSize((int) file.length());
                applicationsSystem.setVersion("1.0.0");
                applicationsSystem.setInstallerPath(FtpClient.WEB_PATH + FtpClient.INSTALLER_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
                applicationsSystem.setSystem(system);
                saveEdits();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Внимание");
                alert.setHeaderText("Выбранный установщик заменит текущий! Продолжить?");
                AtomicBoolean changeExisted = new AtomicBoolean(false);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        changeExisted.set(true);
                    }
                });
                if (changeExisted.get()) {
                    applicationsSystem = existedItem;
                    applicationsSystem.setSize((int) file.length());
                    applicationsSystem.setInstallerPath(FtpClient.WEB_PATH + FtpClient.INSTALLER_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
                    applicationsSystem.setSystem(system);
                    saveEdits();
                }
            }
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
                new FileChooser.ExtensionFilter("Debian", "*.deb"),
                new FileChooser.ExtensionFilter("Windows", "*.exe"));
        fileChooser.setTitle("Выбрать установщик");
        File file = fileChooser.showOpenDialog(StageInitializer.getStage());

        if(file != null) {
            loadFile(file);
        }
    }
}
