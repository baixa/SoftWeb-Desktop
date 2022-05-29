package com.softweb.desktop.controllers;

import com.softweb.desktop.controllers.components.defaults.ApplicationDefaultCell;
import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.repositories.*;
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

public class PageApplicationController implements Initializable {

    private static ApplicationRepository applicationRepository;
    private static DeveloperRepository developerRepository;

    @FXML
    public Button btnFilter;

    @FXML
    public Button btnClear;

    @FXML
    public Button btnHelp;

    @FXML
    private ComboBox<String> comboDeveloper;

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

        List<Developer> developers = new ArrayList<>();
        developerRepository.findAll().forEach(developers::add);
        comboDeveloper.setItems(FXCollections.observableArrayList(developers.stream().map(Developer::getUsername).collect(Collectors.toList())));
        applicationRepository.findAll().forEach(applications::add);
        listApplications.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, o, t1) -> Platform.runLater(() -> listApplications.getSelectionModel().select(-1)));

        renderApplicationList(applications);
        checkRepositories();
    }

    private void checkRepositories() {
        OperationSystemRepository operationSystemRepository = DataService.getOperationSystemRepository();
        List<OperationSystem> operationSystems = new ArrayList<>();
        operationSystemRepository.findAll().forEach(operationSystems::add);

        ApplicationImageRepository applicationImageRepository = DataService.getApplicationImageRepository();
        List<ApplicationImage> applicationImages = new ArrayList<>();
        applicationImageRepository.findAll().forEach(applicationImages::add);

        ApplicationSystemsRepository applicationSystemsRepository = DataService.getApplicationSystemsRepository();
        List<ApplicationsSystems> applicationsSystems = new ArrayList<>();
        applicationSystemsRepository.findAll().forEach(applicationsSystems::add);

        System.out.println(1);
    }

    private void renderApplicationList(List<Application> applications) {
        ObservableList<Application> applicationObservableList = FXCollections.observableArrayList();
        applicationObservableList.addAll(applications);
        listApplications.setItems(applicationObservableList);
        listApplications.setCellFactory(applicationListView -> new ApplicationDefaultCell());
    }

    public void btnFilterClick(ActionEvent actionEvent) {
        String searchName = tbSearch.textProperty().getValue();
        String searchDeveloper = comboDeveloper.valueProperty().getValue();

        List<Application> filteredApplications = applications;

        if(searchName != null && !searchName.equals(""))
            filteredApplications = filteredApplications.stream()
                    .filter(app -> app.getName().toLowerCase().contains(searchName.toLowerCase()))
                    .collect(Collectors.toList());

        if(searchDeveloper != null && !searchDeveloper.equals(""))
            filteredApplications = filteredApplications.stream()
                    .filter(app -> app.getDeveloper().getUsername().equals(searchDeveloper))
                    .collect(Collectors.toList());

        renderApplicationList(filteredApplications);

    }

    public void btnClearClick(ActionEvent actionEvent){
        tbSearch.textProperty().setValue(null);
        comboDeveloper.valueProperty().setValue(null);

        renderApplicationList(applications);
    }

    public void btnHelpClick(ActionEvent actionEvent) {
    }
}
