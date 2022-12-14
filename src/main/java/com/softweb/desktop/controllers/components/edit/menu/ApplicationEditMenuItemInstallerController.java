package com.softweb.desktop.controllers.components.edit.menu;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.Installer;
import com.softweb.desktop.database.entity.OperatingSystem;
import com.softweb.desktop.database.utils.DBCache;
import com.softweb.desktop.database.utils.DataService;
import com.softweb.desktop.utils.ftp.FtpClient;
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

/**
 * A controller that allows you to interact with the app's installers.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationEditMenuItemInstallerController extends ApplicationEditMenuItem implements Initializable {

    /**
     * FXML node containing an inscription stating that there is no installer for this system
     */
    @FXML
    private Label labelWarning;

    /**
     * FXML node containing the Windows OS logo image
     */
    @FXML
    private ImageView ivWindows;

    /**
     * FXML node containing the Linux OS logo image
     */
    @FXML
    private ImageView ivLinux;

    /**
     * An FXML node containing information about the size of the loader.
     */
    @FXML
    private Label tbSize;

    /**
     * FXML node containing an inscription about the current OS.
     */
    @FXML
    private Label tbOS;

    /**
     * An FXML node containing an inscription about the command to install the loader file.
     */
    @FXML
    private Label tbCommand;

    /**
     * Indicator that the selected OS is Windows.
     */
    private boolean windowsSelected = false;

    /**
     * Associated app installer.
     */
    private Installer installer;

    /**
     * Database cache to perform CRUD operations and save information.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
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
                    tbCommand.textProperty().setValue("?????????????? ?? ????????????????????");

                }
                else {
                    tbSize.textProperty().setValue("???? ??????????????????????");
                    tbOS.textProperty().setValue("???? ??????????????????????");
                    tbCommand.textProperty().setValue("???? ??????????????????????");
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

    /**
     * Update information on the page.
     */
    @Override
    public void refreshContent() {
        fillContent();
    }

    /**
     * The method fills in the information on the page
     */
    private void fillContent() {
        Installer installer = getApplication().getInstallers().stream().filter(appSystem -> appSystem.getSystem().getName().contains("Windows")).findFirst().orElse(null);
        if(installer != null)
        {
            fillInstallerData(installer);
            tbCommand.setStyle("-fx-text-fill: #66666699;");
            tbCommand.textProperty().setValue("?????????????? ?? ????????????????????");
        }
        else {
            tbSize.textProperty().setValue("???? ??????????????????????");
            tbOS.textProperty().setValue("???? ??????????????????????");
            tbCommand.textProperty().setValue("???? ??????????????????????");
            labelWarning.setVisible(true);
        }
        windowsSelected = true;
    }

    /**
     * The method fills in information about the installer on the page.
     *
     * @param installer Associated installer
     */
    private void fillInstallerData(Installer installer) {
        labelWarning.setVisible(false);
        double size = installer.getSize();
        size = (size / 1024) / 1024;
        DecimalFormat decimalFormat = new DecimalFormat("###0.00");
        tbSize.textProperty().setValue(decimalFormat.format(size) + " ????");
        tbOS.textProperty().setValue(installer.getSystem().getName());
    }

    /**
     * The method saves application changes to the database.
     */
    public void saveEdits() {
        if(getApplication().getInstallers() == null)
            getApplication().setInstallers(new HashSet<>());

        if(installer.getSystem().getInstallers() == null)
            installer.getSystem().setInstallers(new HashSet<>());

        DataService.saveInstaller(installer);
        DataService.saveOperationSystem(installer.getSystem());
        getApplication().getInstallers().add(installer);
        installer.getSystem().getInstallers().add(installer);
        updateApplication();
    }

    /**
     * The method uploads the selected file to the FTP server.
     *
     * @param file Selected file
     */
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
                alert.setTitle("????????????????");
                alert.setHeaderText("?????????????????? ???????????????????? ?????????????? ??????????????! ?????????????????????");
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
            alert.setTitle("????????????");
            alert.setHeaderText("???????? ????????????????????!");
            alert.show();
        }
    }

    /**
     * The method opens a dialog box for selecting an installer for further loading into the system.
     *
     * Supported formats: EXE, DEB.
     */
    public void fileDialogOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Debian", "*.deb"),
                new FileChooser.ExtensionFilter("Windows", "*.exe"));
        fileChooser.setTitle("?????????????? ????????????????????");
        File file = fileChooser.showOpenDialog(StageInitializer.getStage());

        if(file != null) {
            loadFile(file);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setTitle("??????????????????");
            alert.setHeaderText("????????????!");
            alert.show();
        }

    }
}
