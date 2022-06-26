package com.softweb.desktop.controllers.components.cell;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.edit.ApplicationEditController;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Класс контроллер ячеек приложения, которые отображаются в списке приложений
 * в панели администратора (разработчика) этого приложения.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public class ApplicationUserCell extends AbstractApplicationCell {

    /**
     * FXML кнопка, открывающая струницу редактирования приложения.
     *
     * @see ApplicationEditController
     */
    @FXML
    private Button btnEdit;

    /**
     * FXML кнопка, удаляющая приложение.
     */
    @FXML
    private Button btnRemove;

    /**
     * Инициализирует новую пустую ячейку.
     */
    public ApplicationUserCell() {
        super();
    }

    @Override
    protected void loadFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/UserApplicationItemLayout.fxml"));
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод изменяет дизайн ячейки на свой.
     *
     * @param application Связанное приложение
     * @param isEmpty Индикатор, что ячейка пустая
     */
    @Override
    @Transactional
    protected void updateItem(Application application, boolean isEmpty) {
        super.updateItem(application, isEmpty);
        setId(null);

        fillCellContent(application, isEmpty);

        btnEdit.setOnAction(actionEvent -> {
            Initializable controller = StageInitializer.navigate("/layout/PageApplicationEdit");
            ((ApplicationEditController) controller).setApplication(application);
            ((ApplicationEditController) controller).generatePage();
        });

        btnRemove.setOnAction(event -> {
            if(application != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Внимание!");
                alert.setHeaderText("Вы уверены, что хотите удалить приложение: " + application.getName() + "? \nОтменить действие будет невозможно!");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        DataService.deleteApplication(application);
                        Alert removeAlert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
                        removeAlert.setTitle("Успешно!");
                        removeAlert.setHeaderText("Приложение удалено!");
                        removeAlert.show();
                    }
                });
                StageInitializer.navigate("/layout/PageUserApplicationsLayout");
            }
        });
    }
}
