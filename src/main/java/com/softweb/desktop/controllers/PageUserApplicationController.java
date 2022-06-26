package com.softweb.desktop.controllers;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.controllers.components.cell.ApplicationUserCell;
import com.softweb.desktop.controllers.edit.ApplicationEditController;
import com.softweb.desktop.controllers.utils.NodeLimiters;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.DBCache;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PageUserApplicationController implements Initializable {
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

    private static DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.applications = dbCache.getApplications();
        this.applications = applications.stream()
                .filter(application -> application.getDeveloper().getUsername().equals(Authorization.getCurrentUser().getUsername()))
                .collect(Collectors.toList());
        listApplications.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, o, t1) -> Platform.runLater(() -> listApplications.getSelectionModel().select(-1)));
        NodeLimiters.addTextLimiter(tbSearch, 30);

        renderApplicationList(applications);
    }

    private void renderApplicationList(List<Application> applications) {
        ObservableList<Application> applicationObservableList = FXCollections.observableArrayList();
        applicationObservableList.addAll(applications);
        listApplications.setItems(applicationObservableList);
        listApplications.setCellFactory(applicationListView -> new ApplicationUserCell());
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

    public void addApplication(ActionEvent event) {
        Application application = new Application();
        application.fillStarterInformation();
        Initializable controller = StageInitializer.navigate("/layout/PageApplicationEdit");
        ((ApplicationEditController) controller).setApplication(application);
        ((ApplicationEditController) controller).generatePage();
    }

    public void openDiagramPage() {
        Initializable controller = StageInitializer.navigate("/layout/PageUserApplicationsDiagram");
    }
}
