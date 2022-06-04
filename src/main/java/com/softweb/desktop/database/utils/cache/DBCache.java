package com.softweb.desktop.database.utils.cache;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.database.entity.License;
import com.softweb.desktop.database.entity.OperationSystem;
import com.softweb.desktop.database.utils.services.DataService;
import com.softweb.desktop.utils.ftp.FtpClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBCache {

    private List<Application> applications;
    private List<Developer> developers;
    private List<OperationSystem> systems;
    private List<License> licenses;

    private static DBCache cache;

    private DBCache() {
        fillData();
        loadApplicationsImages();
        cache = this;
    }

    private void loadApplicationsImages() {
        FtpClient.loadApplicationsImages(applications);
    }

    private void loadApplicationData(Application application) {
        FtpClient.updateApplicationData(application);
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

    public static DBCache getCache() {
        if(cache == null)
            initialize();
        return cache;
    }

    private static void initialize() {
        cache = new DBCache();
    }

    public static void clear() {
        initialize();
    }

    public static void updateApplication(Application application) {
        DBCache.getCache().loadApplicationData(application);
    }

    public List<Application> getApplications() {
        return applications;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public List<OperationSystem> getSystems() {
        return systems;
    }
}
