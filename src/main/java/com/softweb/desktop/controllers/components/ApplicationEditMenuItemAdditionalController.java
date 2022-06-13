package com.softweb.desktop.controllers.components;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ApplicationEditMenuItemAdditionalController extends ApplicationEditMenuItem implements Initializable {

    @FXML
    private ComboBox<String> cbLicense;

    @FXML
    private Label tbDate;

    private List<License> licenses;

    private static DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.licenses = dbCache.getLicenses();
        cbLicense.setItems(FXCollections.observableList(licenses.stream().map(License::getName).collect(Collectors.toList())));
    }

    @Override
    public void refreshContent() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        this.tbDate.setText(dateFormat.format(getApplication().getLastUpdate()));
        if (getApplication().getLicense() != null)
            cbLicense.getSelectionModel().select(getApplication().getLicense().getName());
    }

    public void saveEdits() {
        if(cbLicense.getValue() != null)
            getApplication().setLicense(licenses.stream().filter(license -> license.getName().equals(this.cbLicense.getValue())).findFirst().orElse(null));
        updateApplication();
    }

    public void btnRemove(ActionEvent actionEvent) {

    }

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
