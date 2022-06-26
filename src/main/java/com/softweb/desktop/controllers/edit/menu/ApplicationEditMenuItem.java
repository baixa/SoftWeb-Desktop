package com.softweb.desktop.controllers.edit.menu;

import com.softweb.desktop.database.entity.Application;
import com.softweb.desktop.database.utils.DBCache;
import com.softweb.desktop.database.utils.DataService;

import java.util.Date;


/**
 * Абстрактный класс страницы-элемента редактирования приложения.
 *
 * Класс содержит ссылку на редактируемое приложение и кэш, для сохранения изменений.
 * Также он содержит методы для взаимодействия с приложением.
 *
 * @author Максимчук И.
 * @version 1.0
 */
public abstract class ApplicationEditMenuItem {


    /**
     * Связанное приложение
     *
     * @see Application
     */
    @lombok.Getter
    @lombok.Setter
    protected Application application;

    /**
     * Кэш базы данных для сохранения изменений
     *
     * @see DBCache
     */
    protected DBCache dbCache = DBCache.getCache();


    /**
     * Метод обновляет приложение и сохраняет изменения.
     * Также он обновляет дату обновления приложения на текущую и
     * после сохранения вызывает обновление кеша и обновляет контент на странице.
     *
     * @see DBCache#clear()
     * @see this#refreshContent()
     */
    public void updateApplication() {
        this.application.setLastUpdate(new Date());
        DataService.saveApplication(getApplication());
        dbCache.getApplications().stream()
                .filter(item -> item.getId().equals(getApplication().getId()))
                .findFirst().ifPresent(this::setApplication);
        dbCache.clear();
        refreshContent();
    }


    /**
     * Метод обновляет информацию на странице.
     */
    public abstract void refreshContent();
}
