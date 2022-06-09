package com.softweb.desktop.controllers.components;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.cache.DBCache;
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
        DataService.saveApplication(getApplication());
        Application updated = DBCache.getCache().getApplications().stream()
                .filter(item -> item.getId().equals(getApplication().getId()))
                .findFirst()
                .orElse(null);
        if (updated == null) {
            DBCache.clear();
        }
        else {
            setApplication(updated);
        }
        refreshContent();
    }

    public abstract void refreshContent();

    public abstract void saveEdits();
}
