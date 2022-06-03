package com.softweb.desktop.database.utils.services;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.repositories.*;
import com.softweb.desktop.database.utils.cache.DBCache;
import org.springframework.stereotype.Component;

@Component
public class DataService {

    private static DeveloperRepository developerRepository;
    private static ApplicationRepository applicationRepository;
    private static OperationSystemRepository operationSystemRepository;
    private static ApplicationImageRepository applicationImageRepository;
    private static ApplicationSystemsRepository applicationSystemsRepository;
    private static LicenseRepository licenseRepository;

    public DataService(DeveloperRepository developerRepository, ApplicationRepository applicationRepository,
                       OperationSystemRepository operationSystemRepository, ApplicationImageRepository applicationImageRepository,
                       ApplicationSystemsRepository applicationSystemsRepository, LicenseRepository licenseRepository) {
        DataService.developerRepository = developerRepository;
        DataService.applicationRepository = applicationRepository;
        DataService.operationSystemRepository = operationSystemRepository;
        DataService.applicationImageRepository = applicationImageRepository;
        DataService.applicationSystemsRepository = applicationSystemsRepository;
        DataService.licenseRepository = licenseRepository;
    }

    public static DeveloperRepository getDeveloperRepository() {
        return developerRepository;
    }

    public static ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }

    public static OperationSystemRepository getOperationSystemRepository() {
        return operationSystemRepository;
    }

    public static ApplicationImageRepository getApplicationImageRepository() {
        return applicationImageRepository;
    }

    public static ApplicationSystemsRepository getApplicationSystemsRepository() {
        return applicationSystemsRepository;
    }

    public static LicenseRepository getLicenseRepository() {
        return licenseRepository;
    }

    public static void updateApplication (Application application) {
        applicationRepository.save(application);
        DBCache.clear();
    }

    public static void deleteApplication (Application application) {
        applicationRepository.delete(application);
        DBCache.clear();
    }

}
