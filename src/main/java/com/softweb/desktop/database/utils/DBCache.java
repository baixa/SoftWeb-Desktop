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
 * Кэш выполняет операции по «кэшированию» данных для увеличения скорости обработки
 * данных базы данных, не прибегая к постоянному доступу к ней.
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Component
public class DBCache {

    /**
     * Список приложений
     */
    private List<Application> applications;

    /**
     * Список разработчиков
     */
    private List<Developer> developers;

    /**
     * Список операционных систем
     */
    private List<OperatingSystem> systems;

    /**
     * Список лицензий
     */
    private List<License> licenses;

    /**
     * Загрузить изображение приложения на основе его URL и сохранить его в объекте класса Application Image
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
     * Инициализировать списки и заполнить их данными из базы данных
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
     * Инициализировать кэш и заполнить его текущими данными из БД
     */
    private void initialize() {
        fillData();
        loadApplicationsImages();
    }

    /**
     * Очистить кэш
     */
    public void clear() {
        initialize();
    }

    /**
     * Загрузить полный список разработчиков из БД
     */
    public void loadListOfDevelopers() {
        this.developers = new ArrayList<>();
        DataService.getDeveloperRepository().findAll().forEach(developers::add);
    }

    /**
     * Получить список приложений
     *
     * @return Список приложений
     */
    public List<Application> getApplications() {
        if(applications == null)
            initialize();
        return applications;
    }

    /**
     * Получить список разработчиков
     *
     * @return Список разработчиков
     */
    public List<Developer> getDevelopers() {
        if(developers == null)
            initialize();
        return developers;
    }

    /**
     * Получить список лицензий
     *
     * @return Список лицензий
     */
    public List<License> getLicenses() {
        if(licenses == null)
            initialize();
        return licenses;
    }

    /**
     * Получить список операционных систем
     *
     * @return Список ОС
     */
    public List<OperatingSystem> getSystems() {
        if(systems == null)
            initialize();
        return systems;
    }

    /**
     * Получить объект класса DBCache
     *
     * @return DBCache
     */
    public static DBCache getCache() {
        return JavaFXMain.getApplicationContext().getBean(DBCache.class);
    }
}
