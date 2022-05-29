package com.softweb.desktop.controllers.components.user;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.auth.Authorization;
import com.softweb.desktop.controllers.ApplicationEditController;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.services.DataService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ApplicationUserCell extends ListCell<Application> {
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
    private Button btnEdit;

    @FXML
    private Button btnRemove;

    public ApplicationUserCell() {
        loadFXML();
    }

    private void loadFXML(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/UserApplicationItemLayout.fxml"));
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
            this.applicationLogo.setImage(new Image(application.getLogoPath()));
            this.labelApplicationName.setText(application.getName());
            this.labelShortDescription.setText(application.getShortDescription());
            this.labelDeveloper.setText(application.getDeveloper().getFullName());
        }

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
                        DataService.getApplicationRepository().delete(application);
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
