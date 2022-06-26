package com.softweb.desktop.controllers;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.controllers.components.cell.ApplicationDefaultCell;
import com.softweb.desktop.controllers.utils.NodeLimiters;
import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.utils.DBCache;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Класс-контроллер, содержащий список приложений для каталога
 */
public class ListDownloadedApplicationsController implements Initializable {

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
     * FXML узел, содержащий список разработчиков
     */
    @FXML
    private ComboBox<String> comboDeveloper;

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
    private static DBCache dbCache = JavaFXMain.getApplicationContext().getBean(DBCache.class);

    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.applications = dbCache.getApplications();
        List<Developer> developers = dbCache.getDevelopers();
        comboDeveloper.setItems(FXCollections.observableArrayList(developers.stream().map(Developer::getUsername).collect(Collectors.toList())));
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
        listApplications.setCellFactory(applicationListView -> new ApplicationDefaultCell());
    }

    /**
     * Метод фильтрует список приложений по полю "Поиск" и "Разработчик"
     */
    public void btnFilterClick() {
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

    /**
     * Метод выполняет сброс фильтров поиска
     */
    public void btnClearClick(){
        tbSearch.textProperty().setValue(null);
        comboDeveloper.valueProperty().setValue(null);

        renderApplicationList(applications);
    }
}
