package com.softweb.desktop.controllers.components.user;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.controllers.ApplicationEditController;
import com.softweb.desktop.controllers.components.cell.AbstractApplicationCell;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * ApplicationDefaultCell class is controller of application cells, that are visible
 * on user applications list page (ListDownloadedApplicationsController).
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public class ApplicationUserCell extends AbstractApplicationCell {

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
