package com.softweb.desktop.database.utils.cache;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.entity.Developer;
import com.softweb.desktop.services.DataService;

import java.util.List;

public class DBCache {

    private List<Application> applications;
    private List<Developer> developers;

    private static DBCache cache;

    private DBCache() {
        Iterable<Application> applicationIterable = DataService.getApplicationRepository().findAll();
        applicationIterable.forEach(applications::add);

        Iterable<Developer> developerIterable = DataService.getDeveloperRepository().findAll();
        developerIterable.forEach(developers::add);
    }

    public static DBCache getCache() {
        if(cache == null)
            cache = new DBCache();

        return cache;
    }

    public static void clean() {
        cache = null;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}
