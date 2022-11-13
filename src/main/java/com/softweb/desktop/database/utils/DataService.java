package com.softweb.desktop.database.utils;

import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.repositories.*;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * The class contains a method for interacting with entity repositories
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Component
public class DataService {

    /**
     * Developer class repository for performing CRUD operations
     */
    @lombok.Getter
    private static DeveloperRepository developerRepository;

    /**
     * Application class repository for performing CRUD operations
     */
    @lombok.Getter
    private static ApplicationRepository applicationRepository;

    /**
     * Operating System class repository for performing CRUD operations
     */
    @lombok.Getter
    private static OperatingSystemRepository operatingSystemRepository;

    /**
     * Application Image class repository for performing CRUD operations
     */
    private static ApplicationImageRepository applicationImageRepository;

    /**
     * Installer class repository for performing CRUD operations
     */
    @lombok.Getter
    private static InstallerRepository installerRepository;

    /**
     * License class repository for performing CRUD operations
     */
    @lombok.Getter
    private static LicenseRepository licenseRepository;

    /**
     * A database cache for performing CRUD operations.
     *
     * @see DBCache
     */
    private static DBCache dbCache;

    /**
     * Initializes the DataService object and populates the fields with values
     *
     * @param developerRepository Developer class repository
     * @param applicationRepository Application class repository
     * @param operatingSystemRepository Operating System class repository
     * @param applicationImageRepository Application Image class repository
     * @param installerRepository Installer class repository
     * @param licenseRepository License class repository
     * @param dbCache Database cache
     */
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

    /**
     * Save application to database
     *
     * @param application The application to be saved
     */
    public static void saveApplication(Application application) {
        applicationRepository.save(application);
    }

    /**
     * Save application image to DB
     *
     * @param applicationImage The object to save
     */
    public static void saveApplicationImage(ApplicationImage applicationImage) {
        applicationImageRepository.save(applicationImage);
    }

    /**
     * Save application installer to DB
     *
     * @param installer Stored object
     */
    public static void saveInstaller(Installer installer) {
        installerRepository.save(installer);
    }

    /**
     * Save operating system to database
     *
     * @param operatingSystem Object to store
     */
    public static void saveOperationSystem(OperatingSystem operatingSystem) {
        operatingSystemRepository.save(operatingSystem);
    }

    /**
     * Save developer to database
     *
     * @param developer Stored object
     */
    public static void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
        dbCache.loadListOfDevelopers();
    }

    /**
     * Delete application from database
     *
     * @param application Object to remove
     */
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

    /**
     * Remove application image from database
     *
     * @param applicationImage Object to remove
     */
    public static void deleteApplicationImage (ApplicationImage applicationImage) {
        applicationImageRepository.delete(applicationImage);
    }
}
