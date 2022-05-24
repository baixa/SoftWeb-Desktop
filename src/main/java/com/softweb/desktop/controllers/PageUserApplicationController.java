package com.softweb.desktop.controllers;

import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.controllers.components.defaults.ApplicationDefaultCell;
import com.softweb.desktop.controllers.components.user.ApplicationUserCell;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.repositories.ApplicationRepository;
import com.softweb.desktop.database.repositories.DeveloperRepository;
import com.softweb.desktop.services.DataService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PageUserApplicationController implements Initializable {
    private static ApplicationRepository applicationRepository;
    private static DeveloperRepository developerRepository;

    @FXML
    public Button btnFilter;

    @FXML
    public Button btnClear;

    @FXML
    public Button btnHelp;

    @FXML
    private TextField tbSearch;

    @FXML
    private ListView<Application> listApplications;

    private List<Application> applications;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        developerRepository = DataService.getDeveloperRepository();
        applicationRepository = DataService.getApplicationRepository();
        this.applications = new ArrayList<>();

        applications.addAll(applicationRepository.findByDeveloper(Authorization.getCurrentUser()));
        listApplications.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, o, t1) -> Platform.runLater(() -> listApplications.getSelectionModel().select(-1)));

        renderApplicationList(applications);
    }

    private void renderApplicationList(List<Application> applications) {
        ObservableList<Application> applicationObservableList = FXCollections.observableArrayList();
        applicationObservableList.addAll(applications);
        listApplications.setItems(applicationObservableList);
        listApplications.setCellFactory(applicationListView -> new ApplicationDefaultCell());
    }

    public void btnFilterClick(ActionEvent actionEvent) {
        String searchName = tbSearch.textProperty().getValue();

        List<Application> filteredApplications = applications;

        if(searchName != null && !searchName.equals(""))
            filteredApplications = filteredApplications.stream()
                    .filter(app -> app.getName().toLowerCase().contains(searchName.toLowerCase()))
                    .collect(Collectors.toList());

        renderApplicationList(filteredApplications);
    }

    public void btnClearClick(ActionEvent actionEvent){
        tbSearch.textProperty().setValue(null);
        renderApplicationList(applications);
    }

    public void btnHelpClick(ActionEvent actionEvent) {
    }
}
