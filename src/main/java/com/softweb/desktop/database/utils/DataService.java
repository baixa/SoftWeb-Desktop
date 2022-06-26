package com.softweb.desktop.database.utils;

import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.repositories.*;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * DataService contains tools to manipulate with entity-repositories.
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Component
public class DataService {

    /**
     * Repository interface of Developer class to perform CRUD operations
     */
    private static DeveloperRepository developerRepository;

    /**
     * Repository interface of Application class to perform CRUD operations
     */
    private static ApplicationRepository applicationRepository;

    /**
     * Repository interface of Application Image class to perform CRUD operations
     */
    private static OperatingSystemRepository operatingSystemRepository;

    /**
     * Repository interface of Application Image class to perform CRUD operations
     */
    private static ApplicationImageRepository applicationImageRepository;

    /**
     * Repository interface of Application Image class to perform CRUD operations
     */
    private static InstallerRepository installerRepository;

    /**
     * Repository interface of Application Image class to perform CRUD operations
     */
    private static LicenseRepository licenseRepository;
    private static DBCache dbCache;

    public DataService(DeveloperRepository developerRepository, ApplicationRepository applicationRepository,
                       OperatingSystemRepository operatingSystemRepository, ApplicationImageRepository applicationImageRepository,
                       InstallerRepository installerRepository, LicenseRepository licenseRepository,
                       DBCache dbCache) {
        DataService.developerRepository = developerRepository;
        DataService.applicationRepository = applicationRepository;
        DataService.operatingSystemRepository = operatingSystemRepository;
        DataService.applicationImageRepository = applicationImageRepository;
        DataService.installerRepository = installerRepository;
        DataService.licenseRepository = licenseRepository;
        DataService.dbCache = dbCache;
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
    }

    public static void saveApplicationImage(ApplicationImage applicationImage) {
        applicationImageRepository.save(applicationImage);
    }

    public static void saveApplicationSystem(Installer installer) {
        installerRepository.save(installer);
    }

    public static void saveOperationSystem(OperatingSystem operatingSystem) {
        operatingSystemRepository.save(operatingSystem);
    }

    public static void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
        dbCache.updateDevelopers();
    }

    public static void deleteApplication (Application application) {
        Set<ApplicationImage> images = application.getImages();
        application.setImages(null);
        applicationImageRepository.deleteAll(images);
        Set<Installer> installers = application.getInstallers();
        application.setInstallers(null);
        installers.forEach(installer -> dbCache.getSystems().forEach(system -> system.getInstallers().remove(installer)));
        installerRepository.deleteAll(installers);
        applicationRepository.delete(application);
        dbCache.clear();
    }

    public static void deleteApplicationImage (ApplicationImage applicationImage) {
        applicationImageRepository.delete(applicationImage);
    }

    public static void deleteInstaller (Installer installer) {
        installerRepository.delete(installer);
    }
}
