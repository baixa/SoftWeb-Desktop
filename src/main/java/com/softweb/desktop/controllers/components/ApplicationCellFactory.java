package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entities.Application;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ApplicationCellFactory implements Callback<ListView<Application>, ListCell<Application>> {
    @Override
    public ListCell<Application> call(ListView<Application> applicationListView) {
        return new ApplicationCell();
    }
}
