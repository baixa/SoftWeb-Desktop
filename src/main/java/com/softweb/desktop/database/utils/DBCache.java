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
 * The cache performs data "caching" operations to increase the processing speed
 * of the database data without resorting to constant access to it.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Component
public class DBCache {

    /**
     * Application List
     */
    private List<Application> applications;

    /**
     * List of developers
     */
    private List<Developer> developers;

    /**
     * Список операционных систем
     */
    private List<OperatingSystem> systems;

    /**
     * List of licenses
     */
    private List<License> licenses;

    /**
     * Download an application image based on its URL and store it in an object of the Application Image class
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
     * Initialize lists and populate them with data from the database
     */
    private void fillData() {
        applications = new ArrayList<>();
        developers = new ArrayList<>();
        systems = new ArrayList<>();
        licenses = new ArrayList<>();

        DataService.getApplicationRepository().findAll().forEach(applications::add);
        DataService.getDeveloperRepository().findAll().forEach(developers::add);
        DataService.getOperatingSystemRepository().findAll().forEach(systems::add);
        DataService.getLicenseRepository().findAll().forEach(licenses::add);
    }

    /**
     * Initialize the cache and fill it with the current data from the database
     */
    private void initialize() {
        fillData();
        loadApplicationsImages();
    }

    /**
     * Clear cache
     */
    public void clear() {
        initialize();
    }

    /**
     * Download the full list of developers from the database
     */
    public void loadListOfDevelopers() {
        this.developers = new ArrayList<>();
        DataService.getDeveloperRepository().findAll().forEach(developers::add);
    }

    /**
     * Get a list of applications
     *
     * @return Application List
     */
    public List<Application> getApplications() {
        if(applications == null)
            initialize();
        return applications;
    }

    /**
     * Get a list of developers
     *
     * @return List of developers
     */
    public List<Developer> getDevelopers() {
        if(developers == null)
            initialize();
        return developers;
    }

    /**
     * Get a list of licenses
     *
     * @return List of licenses
     */
    public List<License> getLicenses() {
        if(licenses == null)
            initialize();
        return licenses;
    }

    /**
     * Get a list of operating systems
     *
     * @return OS list
     */
    public List<OperatingSystem> getSystems() {
        if(systems == null)
            initialize();
        return systems;
    }

    /**
     * Get an object of the DBCache class
     *
     * @return DBCache
     */
    public static DBCache getCache() {
        return JavaFXMain.getApplicationContext().getBean(DBCache.class);
    }
}
