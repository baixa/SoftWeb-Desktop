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
 * Abstract class of application cell that contains fields of FXML layout of cells and methods to fill them content
 */
public abstract class AbstractApplicationCell extends ListCell<Application> {
    /**
     * FXML node that shows application logo image
     *
     * @see Application#getLogo()
     */
    @FXML
    protected ImageView applicationLogo;

    /**
     * FXML node that shows application name
     *
     * @see Application#getName()
     */
    @FXML
    protected Label labelApplicationName;

    /**
     * FXML node that shows application short description
     *
     * @see Application#getShortDescription()
     */
    @FXML
    protected Label labelShortDescription;

    /**
     * FXML node that shows application developer username
     *
     * @see Developer#getUsername()
     */
    @FXML
    protected Label labelDeveloper;

    /**
     * FXML node that contains root element of FXML layout
     */
    @FXML
    protected GridPane rootElement;

    /**
     * Field associates current cell with a specific application
     *
     * @see Application
     */
    protected Application application;

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
     * Initialize new empty cell with default data
     */
    public AbstractApplicationCell() {
        loadFXML();
    }

    /**
     * Load layout of cell
     */
    protected abstract void loadFXML();
}
