package com.softweb.desktop.controllers.components.defaults;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.ApplicationController;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.utils.services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * ApplicationDefaultCell class is controller of application cells, that are visible
 * on main page (ListDownloadedApplicationsController).
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationDefaultCell extends ListCell<Application> {

    /**
     * FXML node that shows application logo image
     *
     * @see Application#getLogo()
     */
    @FXML
    private ImageView applicationLogo;

    /**
     * FXML node that shows application name
     *
     * @see Application#getName()
     */
    @FXML
    private Label labelApplicationName;

    /**
     * FXML node that shows application short description
     *
     * @see Application#getShortDescription()
     */
    @FXML
    private Label labelShortDescription;

    /**
     * FXML node that shows application developer username
     *
     * @see Developer#getUsername()
     */
    @FXML
    private Label labelDeveloper;

    /**
     * FXML node that contains root element of FXML layout
     */
    @FXML
    private GridPane rootElement;

    /**
     * FXML button open application page
     *
     * @see ApplicationController
     */
    @FXML
    private Button btnOpen;


    /**
     * Field associates current cell with a specific application
     */
    private Application application;


    /**
     * Initialize new empty cell with default data
     */
    public ApplicationDefaultCell() {
        loadFXML();
    }

    /**
     * Load layout of cell
     */
    private void loadFXML(){
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
     * Method rebuild cell to set custom design
     *
     * @param application Referenced application
     * @param isEmpty Indicates that cell is empty
     */
    @Override
    protected void updateItem(Application application, boolean isEmpty) {
        super.updateItem(application, isEmpty);
        setId(null);

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

        btnOpen.setOnAction(event -> {
            this.application.view();
            DataService.saveApplication(this.application);
            Initializable controller = StageInitializer.navigate("/layout/PageApplicationLayout");
            if (controller instanceof ApplicationController) {
                ((ApplicationController) controller).setApplication(this.application);
            }
        });
    }
}
