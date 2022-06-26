package com.softweb.desktop.controllers.components.cell;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Абстрактный класс элментов списка приложений, содержащий поля разметки элементов FXML.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public abstract class AbstractApplicationCell extends ListCell<Application> {
    /**
     * FXML узел, содержащий изоброжение логотипа приложения.
     *
     * @see Application#getLogo()
     */
    @FXML
    protected ImageView applicationLogo;

    /**
     * FXML узел, содержащий название приложения.
     *
     * @see Application#getName()
     */
    @FXML
    protected Label labelApplicationName;

    /**
     * FXML узел, содержащий заголовок приложения.
     *
     * @see Application#getShortDescription()
     */
    @FXML
    protected Label labelShortDescription;

    /**
     * FXML узел, содержащий имя разработчика приложения.
     *
     * @see Developer#getUsername()
     */
    @FXML
    protected Label labelDeveloper;

    /**
     * FXML узел, содержащий корневой элемент разметки.
     */
    @FXML
    protected GridPane rootElement;

    /**
     * Ссылка на объект Application, связанный с этой ячейкой.
     *
     * @see Application
     */
    protected Application application;

    /**
     * Метод заполняет ячейку информацией из связанного с ним объекта Application.
     *
     * @param application Связанное приложение
     * @param isEmpty Индикатор, что ячейка пустая
     */
    protected void fillCellContent(Application application, boolean isEmpty) {
        if(isEmpty || application == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            setId("listApplicationCell");
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(rootElement);
            this.applicationLogo.setImage(application.getLogo());
            this.labelApplicationName.setText(application.getName());
            this.labelShortDescription.setText(application.getShortDescription());
            this.labelDeveloper.setText(application.getDeveloper().getFullName());
            this.application = application;
        }
    }

    /**
     * Инициализирует новую пустую ячейку.
     */
    public AbstractApplicationCell() {
        loadFXML();
    }

    /**
     * Загружает разметку ячейки.
     */
    protected abstract void loadFXML();
}
