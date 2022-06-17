package com.softweb.desktop.controllers.components.menu;

import com.softweb.desktop.JavaFXMain;
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
import java.util.*;

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

    private Installer installer;

    private static DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ivLinux.setImage(new Image(String.valueOf(getClass().getResource("/images/linux-gray.png").toURI())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ivWindows.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
               if(!windowsSelected) {
                   fillContent();
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
                    fillInstallerData(installer);
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
        fillContent();
    }

    private void fillContent() {
        Installer installer = getApplication().getInstallers().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Windows")).findFirst().orElse(null);
        if(installer != null)
        {
            fillInstallerData(installer);
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

    private void fillInstallerData(Installer installer) {
        labelWarning.setVisible(false);
        double size = installer.getSize();
        size = (size / 1024) / 1024;
        DecimalFormat decimalFormat = new DecimalFormat("###0.00");
        tbSize.textProperty().setValue(decimalFormat.format(size) + " Мб");
        tbOS.textProperty().setValue(installer.getSystem().getName());
    }

    public void saveEdits() {
        if(getApplication().getInstallers() == null)
            getApplication().setInstallers(new HashSet<>());

        if(installer.getSystem().getInstallers() == null)
            installer.getSystem().setInstallers(new HashSet<>());

        DataService.saveApplicationSystem(installer);
        DataService.saveOperationSystem(installer.getSystem());
        getApplication().getInstallers().add(installer);
        installer.getSystem().getInstallers().add(installer);
        updateApplication();
    }

    private void loadFile(File file) {
        FtpClient ftpClient = JavaFXMain.getApplicationContext().getBean(FtpClient.class);
        try {
            String fileExt = Optional.of(file.getName()).filter(f -> f.contains(".")).map(f -> f.substring(file.getName().lastIndexOf("."))).orElse("");
            ftpClient.open();
            InputStream inputStream = new FileInputStream(file);
            String fileName = java.util.UUID.randomUUID().toString() + fileExt;
            ftpClient.putFileToPath(inputStream, FtpClient.INSTALLER_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
            ftpClient.close();
            OperatingSystem system;
            if (fileExt.equals(".deb")) {
                system = dbCache.getSystems().stream().filter(item -> item.getName().contains("Debian")).findFirst().orElse(null);
            }
            else {
                system = dbCache.getSystems().stream().filter(item -> item.getName().contains("Windows")).findFirst().orElse(null);
            }
            List<Installer> installers = new ArrayList<>(getApplication().getInstallers());
            Installer existedItem = installers.stream()
                    .filter(item -> item.getApplication().getId().equals(getApplication().getId()))
                    .filter(item -> item.getSystem().getId().equals(system.getId()))
                    .findFirst()
                    .orElse(null);
            if (existedItem == null) {
                installer = new Installer();
                installer.setApplication(getApplication());
                installer.setSize((int) file.length());
                installer.setVersion("1.0.0");
                installer.setInstallerPath(FtpClient.WEB_PATH + FtpClient.INSTALLER_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
                installer.setSystem(system);
                saveEdits();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Внимание");
                alert.setHeaderText("Выбранный установщик заменит текущий! Продолжить?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        installer = existedItem;
                        installer.setSize((int) file.length());
                        installer.setInstallerPath(FtpClient.WEB_PATH + FtpClient.INSTALLER_PATH + getApplication().getDeveloper().getUsername() + "/" + getApplication().getName() + "/" + fileName);
                        installer.setSystem(system);
                        saveEdits();
                    }
                });
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setTitle("Результат");
            alert.setHeaderText("Готово!");
            alert.show();
        }

    }
}
