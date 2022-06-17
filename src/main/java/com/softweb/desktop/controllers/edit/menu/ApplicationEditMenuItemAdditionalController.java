package com.softweb.desktop.controllers.edit.menu;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;
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
 * Controller, that allows to change additional information about the application.
 *
 * In addition to editing the license,
 * it contains information about the last editing of the application and the possibility of
 * deleting the application from the system
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationEditMenuItemAdditionalController extends ApplicationEditMenuItem implements Initializable {

    /**
     * FXML node that contains list of licenses' names
     */
    @FXML
    private ComboBox<String> cbLicense;

    /**
     * FXML node that shows application's last update date
     */
    @FXML
    private Label tbDate;

    /**
     * Full list of licenses
     */
    private List<License> licenses;

    /**
     * Database cache designed to perform CRUD operations
     *
     * @see DBCache
     */
    private static final DBCache dbCache = DBCache.getCache();


    /**
     * Called to initialize a controller after its root element has been completely processed.
     *
     * @param url URL used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle Resource bundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.licenses = dbCache.getLicenses();
        cbLicense.setItems(FXCollections.observableList(licenses.stream().map(License::getName).collect(Collectors.toList())));
    }

    /**
     * Update content on page
     */
    @Override
    public void refreshContent() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        this.tbDate.setText(dateFormat.format(getApplication().getLastUpdate()));
        if (getApplication().getLicense() != null)
            cbLicense.getSelectionModel().select(getApplication().getLicense().getName());
    }

    /**
     * Method save edits in database
     */
    public void saveEdits() {
        if(cbLicense.getValue() != null)
            getApplication().setLicense(licenses.stream().filter(license -> license.getName().equals(this.cbLicense.getValue())).findFirst().orElse(null));
        updateApplication();
    }

    /**
     * Method remove referenced application
     */
    public void removeApplication() {
        DataService.deleteApplication(getApplication());
        StageInitializer.navigate("/layout/PageUserApplicationsLayout");
    }


    /**
     * Method perform application's license changing
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
