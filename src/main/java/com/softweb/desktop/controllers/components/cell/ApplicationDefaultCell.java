package com.softweb.desktop.controllers.components.cell;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.ApplicationController;
import com.softweb.desktop.controllers.components.cell.AbstractApplicationCell;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * ApplicationDefaultCell class is controller of application cells, that are visible
 * on main page with applications list.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationDefaultCell extends AbstractApplicationCell {

    /**
     * FXML button open application page
     *
     * @see ApplicationController
     */
    @FXML
    private Button btnOpen;

    /**
     * Initialize new empty application cell
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
     * Method rebuild cell to set custom design
     *
     * @param application Referenced application
     * @param isEmpty Indicates that cell is empty
     */
    @Override
    protected void updateItem(Application application, boolean isEmpty) {
        super.updateItem(application, isEmpty);
        setId(null);

        fillCellContent(application, isEmpty);

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
