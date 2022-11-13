package com.softweb.desktop.controllers;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.controllers.components.cell.ApplicationUserCell;
import com.softweb.desktop.controllers.components.edit.ApplicationEditController;
import com.softweb.desktop.controllers.utils.NodeLimiters;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.DBCache;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller class containing a list of authorized user applications
 */
public class ListUserApplicationsController implements Initializable {

    /**
     * FXML button that searches by filter
     */
    @FXML
    public Button btnFilter;

    /**
     * FXML button clearing filters
     */
    @FXML
    public Button btnClear;

    /**
     * FXML node containing the search string
     */
    @FXML
    private TextField tbSearch;

    /**
     * FXML node containing list of applications
     */
    @FXML
    private ListView<Application> listApplications;

    /**
     * Application List
     */
    private List<Application> applications;

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
        this.applications = dbCache.getApplications();
        if(!Authorization.getCurrentUser().isAdmin()){
            this.applications = this.applications.stream()
                    .filter(application -> application.getDeveloper().getUsername().equals(Authorization.getCurrentUser().getUsername()))
                    .collect(Collectors.toList());
        }
        listApplications.getSelectionModel().selectedIndexProperty().addListener((ChangeListener) (observableValue, o, t1) -> Platform.runLater(() -> listApplications.getSelectionModel().select(-1)));
        NodeLimiters.addTextLimiter(tbSearch, 30);

        renderApplicationList(applications);
    }

    /**
     * The method generates a list of applications
     *
     * @param applications Application source
     */
    private void renderApplicationList(List<Application> applications) {
        ObservableList<Application> applicationObservableList = FXCollections.observableArrayList();
        applicationObservableList.addAll(applications);
        listApplications.setItems(applicationObservableList);
        listApplications.setCellFactory(applicationListView -> new ApplicationUserCell());
    }

    /**
     * The method filters the list of applications by the field "Search" and "Developer"
     */
    public void btnFilterClick() {
        String searchName = tbSearch.textProperty().getValue();

        List<Application> filteredApplications = applications;

        if(searchName != null && !searchName.equals(""))
            filteredApplications = filteredApplications.stream()
                    .filter(app -> app.getName().toLowerCase().contains(searchName.toLowerCase()))
                    .collect(Collectors.toList());

        renderApplicationList(filteredApplications);
    }

    /**
     * The method resets the search filters
     */
    public void btnClearClick(){
        tbSearch.textProperty().setValue(null);
        renderApplicationList(applications);
    }

    /**
     * The method opens the page for adding a new application
     */
    public void addApplication() {
        Application application = new Application();
        application.fillStarterInformation();
        Initializable controller = StageInitializer.navigate("/layout/PageApplicationEdit");
        ((ApplicationEditController) controller).setApplication(application);
        ((ApplicationEditController) controller).generatePage();
    }

    /**
     * The method opens a page with application popularity charts
     */
    public void openDiagramPage() {
        StageInitializer.navigate("/layout/PageUserApplicationsDiagram");
    }
}
