package com.softweb.desktop.controllers.components.defaults;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.ApplicationController;
import com.softweb.desktop.database.entity.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ApplicationDefaultCell extends ListCell<Application> {
    @FXML
    private ImageView applicationLogo;

    @FXML
    private Label labelApplicationName;

    @FXML
    private Label labelShortDescription;

    @FXML
    private Label labelDeveloper;

    @FXML
    private GridPane rootElement;

    @FXML
    private Button btnOpen;

    private Application application;

    public ApplicationDefaultCell() {
        loadFXML();
    }

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

    @Override
    protected void updateItem(Application application, boolean b) {
        super.updateItem(application, b);
        setId(null);

        if(b || application == null) {
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
            Initializable controller = StageInitializer.navigate("/layout/PageApplicationLayout");
            if (controller instanceof ApplicationController) {
                ((ApplicationController) controller).setApplication(application);
            }
        });
    }
}
