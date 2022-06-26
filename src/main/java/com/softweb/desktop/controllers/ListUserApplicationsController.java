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
 * Класс-контроллер, содержащий список приложений авторизованнного пользователя
 */
public class ListUserApplicationsController implements Initializable {

    /**
     * FXML кнопка, выполняющий поиск по фильтру
     */
    @FXML
    public Button btnFilter;

    /**
     * FXML кнопка, очищающая фильтры
     */
    @FXML
    public Button btnClear;

    /**
     * FXML узел, содержащий строку поиска
     */
    @FXML
    private TextField tbSearch;

    /**
     * FXML узел, содержащий  список приложений
     */
    @FXML
    private ListView<Application> listApplications;

    /**
     * Список приложений
     */
    private List<Application> applications;

    /**
     * Кэш базы данных для выполнения CRUD операций и сохранения информации.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
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
     * Метод генерирует список приложений
     * @param applications Источник приложений
     */
    private void renderApplicationList(List<Application> applications) {
        ObservableList<Application> applicationObservableList = FXCollections.observableArrayList();
        applicationObservableList.addAll(applications);
        listApplications.setItems(applicationObservableList);
        listApplications.setCellFactory(applicationListView -> new ApplicationUserCell());
    }

    /**
     * Метод фильтрует список приложений по полю "Поиск" и "Разработчик"
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
     * Метод выполняет сброс фильтров поиска
     */
    public void btnClearClick(){
        tbSearch.textProperty().setValue(null);
        renderApplicationList(applications);
    }

    /**
     * Метод открывает страницу добавления нового приложения
     */
    public void addApplication() {
        Application application = new Application();
        application.fillStarterInformation();
        Initializable controller = StageInitializer.navigate("/layout/PageApplicationEdit");
        ((ApplicationEditController) controller).setApplication(application);
        ((ApplicationEditController) controller).generatePage();
    }

    /**
     * Метод открывает страницу с диаграммами популярности приложений
     */
    public void openDiagramPage() {
        StageInitializer.navigate("/layout/PageUserApplicationsDiagram");
    }
}
