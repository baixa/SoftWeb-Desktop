package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entity.Application;

public abstract class ApplicationEditMenuItem {
    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public abstract void save();
}
