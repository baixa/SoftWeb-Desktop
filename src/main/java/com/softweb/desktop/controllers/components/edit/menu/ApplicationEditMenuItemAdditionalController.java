package com.softweb.desktop.controllers.components.edit.menu;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.utils.DBCache;
import com.softweb.desktop.database.utils.DataService;
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
 * Контроллер, позволяющий менять дополнительную информацию о приложении.
 *
 * В дополнение к редактированию лицензии, контроллер содержит информацию
 * о дате последнего редактирования приложения и позволяет удалить приложение из системы.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public class ApplicationEditMenuItemAdditionalController extends ApplicationEditMenuItem implements Initializable {

    /**
     * FXML узел, содержащий список доступных лицензий.
     */
    @FXML
    private ComboBox<String> cbLicense;

    /**
     * FXML узел, содержащий дату последнего редактирования приложения.
     */
    @FXML
    private Label tbDate;

    /**
     * Список доступных лицензий в системе.
     */
    private List<License> licenses;

    /**
     * Кэш базы данных для выполнения CRUD операций и сохранения информации.
     *
     * @see DBCache
     */
    private static final DBCache dbCache = DBCache.getCache();


    /**
     * Метод предназначен для инициализации контроллера.
     *
     * @param url URL-адрес, используемый для разрешения относительных путей для корневого объекта, или null, если местоположение неизвестно.
     * @param resourceBundle Пакет ресурсов, используемый для локализации корневого объекта, или null, если корневой объект не был локализован.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.licenses = dbCache.getLicenses();
        cbLicense.setItems(FXCollections.observableList(licenses.stream().map(License::getName).collect(Collectors.toList())));
    }

    /**
     * Обновление информации на странице.
     */
    @Override
    public void refreshContent() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        this.tbDate.setText(dateFormat.format(getApplication().getLastUpdate()));
        if (getApplication().getLicense() != null)
            cbLicense.getSelectionModel().select(getApplication().getLicense().getName());
    }

    /**
     * Метод сохраняет изменения приложения в БД.
     */
    public void saveEdits() {
        if(cbLicense.getValue() != null)
            getApplication().setLicense(licenses.stream().filter(license -> license.getName().equals(this.cbLicense.getValue())).findFirst().orElse(null));
        updateApplication();
    }

    /**
     * Метод удаляет связанное приложение.
     */
    public void removeApplication() {
        DataService.deleteApplication(getApplication());
        StageInitializer.navigate("/layout/PageUserApplicationsLayout");
    }


    /**
     * Метод выполняет смену лицензии приложения.
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
