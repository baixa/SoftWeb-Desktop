package com.softweb.desktop.controllers;

import com.softweb.desktop.controllers.components.ApplicationCell;
import com.softweb.desktop.database.DBActivity;
import com.softweb.desktop.database.entities.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PageApplicationController implements Initializable {

    private DBActivity dbActivity;

    @FXML
    private ComboBox<String> comboDeveloper;

    @FXML
    private TextField tbSearch;

    @FXML
    private ListView<Application> listApplications;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbActivity = new DBActivity();

        List<String> developers = dbActivity.getDevelopersUsername();
        comboDeveloper.setItems(FXCollections.observableArrayList(developers));

        List<Application> applications = dbActivity.getApplications();

        listApplications.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, o, t1) -> Platform.runLater(() -> listApplications.getSelectionModel().select(-1)));

        renderApplicationList(applications);
    }

    private void renderApplicationList(List<Application> applications) {
        ObservableList<Application> applicationObservableList = FXCollections.observableArrayList();
        applicationObservableList.addAll(applications);
        listApplications.setItems(applicationObservableList);
        listApplications.setCellFactory(applicationListView -> new ApplicationCell());
    }

    public void btnFilterClick(ActionEvent actionEvent) {
        List<Application> applications = dbActivity.getApplications();

        String searchName = tbSearch.textProperty().getValue();
        String searchDeveloper = comboDeveloper.valueProperty().getValue();

        if(searchName != null && !searchName.equals(""))
            applications = applications.stream()
                    .filter(app -> app.getName().toLowerCase().contains(searchName.toLowerCase()))
                    .collect(Collectors.toList());

        if(searchDeveloper != null && !searchDeveloper.equals(""))
            applications = applications.stream()
                    .filter(app -> app.getDeveloperShortName().equals(searchDeveloper))
                    .collect(Collectors.toList());

        renderApplicationList(applications);

    }

    public void btnClearClick(ActionEvent actionEvent){
        tbSearch.textProperty().setValue(null);
        comboDeveloper.valueProperty().setValue(null);

        renderApplicationList(dbActivity.getApplications());
    }
}
