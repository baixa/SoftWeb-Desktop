package com.softweb.desktop.database.utils.services;

import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.repositories.*;
import com.softweb.desktop.database.utils.cache.DBCache;
import org.springframework.stereotype.Component;

@Component
public class DataService {

    private static DeveloperRepository developerRepository;
    private static ApplicationRepository applicationRepository;
    private static OperatingSystemRepository operatingSystemRepository;
    private static ApplicationImageRepository applicationImageRepository;
    private static InstallerRepository installerRepository;
    private static LicenseRepository licenseRepository;

    public DataService(DeveloperRepository developerRepository, ApplicationRepository applicationRepository,
                       OperatingSystemRepository operatingSystemRepository, ApplicationImageRepository applicationImageRepository,
                       InstallerRepository installerRepository, LicenseRepository licenseRepository) {
        DataService.developerRepository = developerRepository;
        DataService.applicationRepository = applicationRepository;
        DataService.operatingSystemRepository = operatingSystemRepository;
        DataService.applicationImageRepository = applicationImageRepository;
        DataService.installerRepository = installerRepository;
        DataService.licenseRepository = licenseRepository;
    }

    public static DeveloperRepository getDeveloperRepository() {
        return developerRepository;
    }

    public static ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }

    public static OperatingSystemRepository getOperationSystemRepository() {
        return operatingSystemRepository;
    }

    public static ApplicationImageRepository getApplicationImageRepository() {
        return applicationImageRepository;
    }

    public static InstallerRepository getApplicationSystemsRepository() {
        return installerRepository;
    }

    public static LicenseRepository getLicenseRepository() {
        return licenseRepository;
    }

    public static void saveApplication(Application application) {
        applicationRepository.save(application);
        DBCache.updateApplication(application);
    }

    public static void saveApplicationImage(ApplicationImage applicationImage) {
        applicationImageRepository.save(applicationImage);
    }

    public static void deleteApplication (Application application) {
        applicationRepository.delete(application);
    }

    public static void saveApplicationSystem(Installer applicationsSystem) {
        installerRepository.save(applicationsSystem);
    }

    public static void saveOperationSystem(OperatingSystem operatingSystem) {
        operatingSystemRepository.save(operatingSystem);
    }

    public static void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
        DBCache.updateDevelopers();
    }
}
