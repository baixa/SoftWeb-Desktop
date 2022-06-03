package com.softweb.desktop.controllers.components;

import com.softweb.desktop.StageInitializer;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.services.DataService;

import java.util.Date;

public abstract class ApplicationEditMenuItem {
    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void updateApplication() {
        this.application.setLastUpdate(new Date());
        DataService.updateApplication(getApplication());
        StageInitializer.navigate("/layout/PageUserApplicationsLayout");
    }

    public abstract void refreshContent();

    public abstract void saveEdits();
}
