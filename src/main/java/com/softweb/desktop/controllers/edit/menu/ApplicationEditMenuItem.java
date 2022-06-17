package com.softweb.desktop.controllers.edit.menu;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.cache.DBCache;
import com.softweb.desktop.database.utils.services.DataService;

import java.util.Date;


/**
 * Abstract class of Application Editing Items.
 *
 * It contains reference to editable application and cache to save edits.
 * Also it has some methods to manipulate with application.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public abstract class ApplicationEditMenuItem {


    /**
     * Related application.
     *
     * @see Application
     */
    @lombok.Getter
    @lombok.Setter
    protected Application application;

    /**
     * Database cache to save edits
     *
     * @see DBCache
     */
    protected DBCache dbCache = DBCache.getCache();


    /**
     * Method update application to save edits.
     * Also it change application's field of last update on current date.
     * After saving it calls clearing of cache and update content on page.
     *
     * @see DBCache#clear()
     * @see this#refreshContent()
     */
    public void updateApplication() {
        this.application.setLastUpdate(new Date());
        DataService.saveApplication(getApplication());
        dbCache.getApplications().stream()
                .filter(item -> item.getId().equals(getApplication().getId()))
                .findFirst().ifPresent(this::setApplication);
        dbCache.clear();
        refreshContent();
    }


    /**
     * Method refresh content on EditMenuItem page.
     */
    public abstract void refreshContent();
}
