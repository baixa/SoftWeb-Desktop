package com.softweb.desktop.controllers.components.edit.menu;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.utils.DBCache;
import com.softweb.desktop.database.utils.DataService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * A controller that allows you to change additional information about the application.
 *
 * In addition to editing the license, the controller contains information about the date the application
 * was last edited and allows you to remove the application from the system.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationEditMenuItemAdditionalController extends ApplicationEditMenuItem implements Initializable {

    /**
     * An FXML node containing a list of available licenses.
     */
    @FXML
    private ComboBox<String> cbLicense;

    /**
     * An FXML node containing the date the application was last modified.
     */
    @FXML
    private Label tbDate;

    /**
     * List of available licenses in the system.
     */
    private List<License> licenses;

    /**
     * Database cache to perform CRUD operations and save information.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = DBCache.getCache();


    /**
     * The method is designed to initialize the controller.
     *
     * @param url The URL used to resolve relative paths to the root object, or null if the location is unknown.
     * @param resourceBundle The resource bundle used to localize the root object, or null if the root object has not been localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.licenses = dbCache.getLicenses();
        cbLicense.setItems(FXCollections.observableList(licenses.stream().map(License::getName).collect(Collectors.toList())));
    }

    /**
     * Update information on the page.
     */
    @Override
    public void refreshContent() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        this.tbDate.setText(dateFormat.format(getApplication().getLastUpdate()));
        if (getApplication().getLicense() != null)
            cbLicense.getSelectionModel().select(getApplication().getLicense().getName());
    }

    /**
     * Метод сохраняет изменения приложения в БД.
     */
    public void saveEdits() {
        if(cbLicense.getValue() != null)
            getApplication().setLicense(licenses.stream().filter(license -> license.getName().equals(this.cbLicense.getValue())).findFirst().orElse(null));
        updateApplication();
    }

    /**
     * Метод удаляет связанное приложение.
     */
    public void removeApplication() {
        DataService.deleteApplication(getApplication());
        StageInitializer.navigate("/layout/PageUserApplicationsLayout");
    }


    /**
     * Метод выполняет смену лицензии приложения.
     */
    public void changeLicense() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Смена лицензии");
        alert.setHeaderText("Вы уверены, что хотите поменять лицензию?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                saveEdits();
            }
        });

        alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Результат");
        alert.setHeaderText("Готово!");
        alert.show();
    }
}
