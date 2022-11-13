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
 * An abstract application list item class containing FXML element markup fields.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public abstract class AbstractApplicationCell extends ListCell<Application> {
    /**
     * An FXML node containing the application's logo image.
     *
     * @see Application#getLogo()
     */
    @FXML
    protected ImageView applicationLogo;

    /**
     * An FXML node containing the name of the application.
     *
     * @see Application#getName()
     */
    @FXML
    protected Label labelApplicationName;

    /**
     * An FXML node containing the application title.
     *
     * @see Application#getShortDescription()
     */
    @FXML
    protected Label labelShortDescription;

    /**
     * An FXML node containing the name of the application developer.
     *
     * @see Developer#getUsername()
     */
    @FXML
    protected Label labelDeveloper;

    /**
     * An FXML node containing the root markup element.
     */
    @FXML
    protected GridPane rootElement;

    /**
     * A reference to the Application object associated with this cell.
     *
     * @see Application
     */
    protected Application application;

    /**
     * The method populates the cell with information from its associated Application object.
     *
     * @param application Related application
     * @param isEmpty An indicator that the cell is empty
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
     * Initializes a new empty cell.
     */
    public AbstractApplicationCell() {
        loadFXML();
    }

    /**
     * Loads the cell layout.
     */
    protected abstract void loadFXML();
}
