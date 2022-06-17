package com.softweb.desktop.database.utils.cache;

import com.softweb.desktop.JavaFXMain;
import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.entity.OperatingSystem;
import com.softweb.desktop.database.utils.services.DataService;
import javafx.scene.image.Image;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBCache {

    private List<Application> applications;
    private List<Developer> developers;
    private List<OperatingSystem> systems;
    private List<License> licenses;

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

    private void loadDevelopers() {
        this.developers = new ArrayList<>();
        DataService.getDeveloperRepository().findAll().forEach(developers::add);
    }

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

    private void initialize() {
        fillData();
        loadApplicationsImages();
    }

    public void clear() {
        initialize();
    }

    public void updateDevelopers() {
        loadDevelopers();
    }

    public List<Application> getApplications() {
        if(applications == null)
            initialize();
        return applications;
    }

    public List<Developer> getDevelopers() {
        if(developers == null)
            initialize();
        return developers;
    }

    public List<License> getLicenses() {
        if(licenses == null)
            initialize();
        return licenses;
    }

    public List<OperatingSystem> getSystems() {
        if(systems == null)
            initialize();
        return systems;
    }

    public static DBCache getCache() {
        return JavaFXMain.getApplicationContext().getBean(DBCache.class);
    }
}
