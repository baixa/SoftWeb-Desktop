package com.softweb.desktop.database.utils;

import com.softweb.desktop.database.entity.*;
import com.softweb.desktop.database.repositories.*;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Класс содержит метода для взаимодействия с репозиториями сущностей
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Component
public class DataService {

    /**
     * Репозиторий класса Developer для выполнения CRUD операций
     */
    @lombok.Getter
    private static DeveloperRepository developerRepository;

    /**
     * Репозиторий класса Application для выполнения CRUD операций
     */
    @lombok.Getter
    private static ApplicationRepository applicationRepository;

    /**
     * Репозиторий класса Operating System для выполнения CRUD операций
     */
    @lombok.Getter
    private static OperatingSystemRepository operatingSystemRepository;

    /**
     * Репозиторий класса Application Image для выполнения CRUD операций
     */
    private static ApplicationImageRepository applicationImageRepository;

    /**
     * Репозиторий класса Installer для выполнения CRUD операций
     */
    @lombok.Getter
    private static InstallerRepository installerRepository;

    /**
     * Репозиторий класса License для выполнения CRUD операций
     */
    @lombok.Getter
    private static LicenseRepository licenseRepository;

    /**
     * Кэш базы данных, предназначенный для выполнений CRUD операций.
     *
     * @see DBCache
     */
    private static DBCache dbCache;

    /**
     * Иициализирует объект DataService и заполняет поля значениями
     *
     * @param developerRepository Репозиторий класса Developer
     * @param applicationRepository Репозиторий класса Application
     * @param operatingSystemRepository Репозиторий класса Operating System
     * @param applicationImageRepository Репозиторий класса Application Image
     * @param installerRepository Репозиторий класса Installer
     * @param licenseRepository Репозиторий класса License
     * @param dbCache Кэш базы данных
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
     * Сохранить приложение в БД
     * @param application Сохраняемое приложение
     */
    public static void saveApplication(Application application) {
        applicationRepository.save(application);
    }

    /**
     * Сохранить изображение приложения в БД
     * @param applicationImage Сохраняемый объект
     */
    public static void saveApplicationImage(ApplicationImage applicationImage) {
        applicationImageRepository.save(applicationImage);
    }

    /**
     * Сохранить установщик приложения в БД
     * @param installer Сохраняемый объект
     */
    public static void saveInstaller(Installer installer) {
        installerRepository.save(installer);
    }

    /**
     * Сохранить опреационную систему в БД
     * @param operatingSystem Сохраняемый объект
     */
    public static void saveOperationSystem(OperatingSystem operatingSystem) {
        operatingSystemRepository.save(operatingSystem);
    }

    /**
     * Сохранить разработчика в БД
     * @param developer Сохраняемый объект
     */
    public static void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
        dbCache.loadListOfDevelopers();
    }

    /**
     * Удалить приложение из БД
     * @param application Удаляемый объект
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
     * Удалить изображение приложения из БД
     * @param applicationImage Удаляемый объект
     */
    public static void deleteApplicationImage (ApplicationImage applicationImage) {
        applicationImageRepository.delete(applicationImage);
    }
}
