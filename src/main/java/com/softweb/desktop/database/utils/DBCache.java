package com.softweb.desktop.database.utils;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.entity.OperatingSystem;
import javafx.scene.image.Image;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * DBCache perform "caching" data to increase the speed of data processing
 * of the database without resorting to constant access to it.
 *
 * It has lists of entity-classes, which are fulled, when cache are initialized.
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Component
public class DBCache {

    /**
     * Full list of applications, that are getting from database.
     */
    private List<Application> applications;

    /**
     * Full list of developers, that are getting from database.
     */
    private List<Developer> developers;

    /**
     * Full list of operating systems, that are getting from database.
     */
    private List<OperatingSystem> systems;

    /**
     * Full list of licenses, that are getting from database.
     */
    private List<License> licenses;

    /**
     * Method loads images in application, that is get image path and create
     * object of Image in entity-class.
     */
    private void loadApplicationsImages() {
        applications.forEach(item -> {
            if(item.getLogoPath() != null) {
                item.setLogo(new Image(item.getLogoPath()));
            }
            if(item.getImages() != null && item.getImages().size() != 0) {
                item.getImages().forEach(image -> {
                    if (image.getPath() != null) {
                        image.setImage(new Image(image.getPath()));
                    }
                });
            }
        });
    }

    /**
     * Method full list of developers by data from DB
     */
    private void loadDevelopers() {
        this.developers = new ArrayList<>();
        DataService.getDeveloperRepository().findAll().forEach(developers::add);
    }

    /**
     * Method initialize lists and filled it by data from DB
     */
    private void fillData() {
        applications = new ArrayList<>();
        developers = new ArrayList<>();
        systems = new ArrayList<>();
        licenses = new ArrayList<>();

        DataService.getApplicationRepository().findAll().forEach(applications::add);
        DataService.getDeveloperRepository().findAll().forEach(developers::add);
        DataService.getOperationSystemRepository().findAll().forEach(systems::add);
        DataService.getLicenseRepository().findAll().forEach(licenses::add);
    }

    /**
     * Method initialize cache and get fresh data from DB
     */
    private void initialize() {
        fillData();
        loadApplicationsImages();
    }

    /**
     * Method clearing cache and fill it fresh data from DB
     */
    public void clear() {
        initialize();
    }

    /**
     * Method refresh list of developers
     */
    public void updateDevelopers() {
        loadDevelopers();
    }

    /**
     * Getting full list of applications
     *
     * @return list of applications
     */
    public List<Application> getApplications() {
        if(applications == null)
            initialize();
        return applications;
    }

    /**
     * Getting full list of developers
     *
     * @return list of developers
     */
    public List<Developer> getDevelopers() {
        if(developers == null)
            initialize();
        return developers;
    }

    /**
     * Getting full list of licenses
     *
     * @return list of licenses
     */
    public List<License> getLicenses() {
        if(licenses == null)
            initialize();
        return licenses;
    }

    /**
     * Getting full list of operating system
     *
     * @return list of systems
     */
    public List<OperatingSystem> getSystems() {
        if(systems == null)
            initialize();
        return systems;
    }

    /**
     * Getting bean of DBCache class
     *
     * @return cache
     */
    public static DBCache getCache() {
        return JavaFXMain.getApplicationContext().getBean(DBCache.class);
    }
}
