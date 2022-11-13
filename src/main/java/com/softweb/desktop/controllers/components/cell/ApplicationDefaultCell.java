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
 * The controller class for application cells that appear in the application download directory.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationDefaultCell extends AbstractApplicationCell {

    /**
     * FXML button that opens the application page
     *
     * @see ApplicationController
     */
    @FXML
    private Button btnOpen;

    /**
     * Initializes a new empty cell.
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
     * The method changes the design of the cell to its own.
     *
     * @param application Related application
     * @param isEmpty An indicator that the cell is empty
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
