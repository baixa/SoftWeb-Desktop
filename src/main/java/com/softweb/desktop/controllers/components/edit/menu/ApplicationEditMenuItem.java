package com.softweb.desktop.controllers.components.edit.menu;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.DBCache;
import com.softweb.desktop.database.utils.DataService;

import java.util.Date;


/**
 * The abstract class of the application editing page element.
 *
 * The class contains a link to the application being edited and a cache to save the changes.
 * It also contains methods for interacting with the application.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
public abstract class ApplicationEditMenuItem {


    /**
     * Related application
     *
     * @see Application
     */
    @lombok.Getter
    @lombok.Setter
    protected Application application;

    /**
     * Database cache to save changes
     *
     * @see DBCache
     */
    protected DBCache dbCache = DBCache.getCache();


    /**
     * The method updates the application and saves the changes.
     * It also updates the application update date to the current one and after saving
     * it causes the cache to be updated and updates the content on the page.
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
     * The method updates the information on the page.
     */
    public abstract void refreshContent();
}
