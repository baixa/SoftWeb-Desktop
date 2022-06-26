package com.softweb.desktop.controllers.components.cell;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.ApplicationController;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Класс-контроллер ячеек приложений, которые отображаются в каталоге
 * для загрузки приложений.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public class ApplicationDefaultCell extends AbstractApplicationCell {

    /**
     * FXML кнопка, открывающая страницу приложения
     *
     * @see ApplicationController
     */
    @FXML
    private Button btnOpen;

    /**
     * Инициализирует новую пустую ячейку.
     */
    public ApplicationDefaultCell() {
        super();
    }

    @Override
    protected void loadFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/DefaultApplicationItemLayout.fxml"));
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
    protected void updateItem(Application application, boolean isEmpty) {
        super.updateItem(application, isEmpty);
        setId(null);

        fillCellContent(application, isEmpty);

        btnOpen.setOnAction(event -> {
            this.application.increaseViewsCounter();
            DataService.saveApplication(this.application);
            Initializable controller = StageInitializer.navigate("/layout/PageApplicationLayout");
            if (controller instanceof ApplicationController) {
                ((ApplicationController) controller).setApplication(this.application);
            }
        });
    }
}
