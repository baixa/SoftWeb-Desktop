package com.softweb.desktop.services;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.repositories.ApplicationRepository;
import com.softweb.desktop.database.repositories.DeveloperRepository;
import org.springframework.stereotype.Component;

@Component
public class DataService {

    private static DeveloperRepository developerRepository;
    private static ApplicationRepository applicationRepository;

    public DataService(DeveloperRepository developerRepository, ApplicationRepository applicationRepository) {
        DataService.developerRepository = developerRepository;
        DataService.applicationRepository = applicationRepository;
    }

    public static DeveloperRepository getDeveloperRepository() {
        return developerRepository;
    }

    public static ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }

    public static void updateApplication (Application application) {
        applicationRepository.save(application);
    }

}
