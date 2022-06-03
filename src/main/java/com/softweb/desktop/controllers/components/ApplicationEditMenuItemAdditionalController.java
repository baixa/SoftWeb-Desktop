package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.utils.services.DataService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Iterable<License> iterable = DataService.getLicenseRepository().findAll();
        this.licenses = new ArrayList<>();
        iterable.forEach(licenses::add);
        cbLicense.setItems(FXCollections.observableList(licenses.stream().map(License::getName).collect(Collectors.toList())));
    }

    @Override
    public void refreshContent() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        this.tbDate.setText(dateFormat.format(getApplication().getLastUpdate()));
        cbLicense.getSelectionModel().select(getApplication().getLicense().getName());
    }

    @Override
    public void saveEdits() {
        if(cbLicense.getValue() != null)
            getApplication().setLicense(licenses.stream().filter(license -> license.getName().equals(this.cbLicense.getValue())).findFirst().orElse(null));
        updateApplication();
    }

    public void btnRemove(ActionEvent actionEvent) {

    }
}
