package com.softweb.desktop.database.entity;

import com.softweb.desktop.auth.Authorization;
import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;
import java.util.*;


/**
 * Класс-сущность Application содержит данные приложений.
 *
 * Класс содержит автоматически генерируемый индентификатор, имя приложения, его описание и заголовок, путь и
 * изображение логотипа приложения, данные о лицензии и данные об аналитике приложений: количество скачиваний и просмотров.
 *
 * Также класс содержит поля, которые связывают приложение с его разработчиком, списком изображений и установщиками приложения.
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "application")
@AllArgsConstructor
public class Application{

    /**
     * Автоматически генерируемый индентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название приложения
     */
    @Column(name = "name")
    private String name;

    /**
     * Заголовок приложения
     */
    @Column(name = "short_description")
    private String shortDescription;

    /**
     * Описание приложения
     */
    @Column(name = "description")
    private String description;

    /**
     * URL изображения логотипа приложения
     */
    @Column(name = "logo_path")
    private String logoPath;

    /**
     * Изображение логотипа
     *
     * @see this#logoPath
     */
    @Transient
    private Image logo;

    /**
     * Дата последнего обновления приложения
     */
    @Column(name = "last_update")
    private Date lastUpdate;

    /**
     * Количество загрузок приложения
     */
    @Column(name = "downloads")
    private int downloads;

    /**
     * Количество просмотров приложения
     */
    @Column(name = "views")
    private int views;

    /**
     * Лицензия приложения
     */
    @ManyToOne
    @JoinColumn(name = "license")
    private License license;

    /**
     * Разработчик приложения
     */
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    /**
     * Список изображений приложения
     */
    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<ApplicationImage> images;

    /**
     * Список установщиков приложения
     */
    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<Installer> installers;

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", downloads=" + downloads +
                ", views=" + views +
                ", license='" + license + '\'' +
                ", developer=" + developer +
                '}';
    }

    /**
     * Метод инициализирует списки класса и заполняет поля приложения начальными данными
     */
    public void fillStarterInformation() {
        images = new HashSet<>();
        installers = new HashSet<>();
        lastUpdate = new Date();
        developer = Authorization.getCurrentUser();
    }

    /**
     * Метод увеличивает количество загрузок приложения
     */
    public void increaseDownloadsCounter() {
        downloads += 1;
    }

    /**
     * Метод увеличивает количество просмотров приложения
     */
    public void increaseViewsCounter() {
        views += 1;
    }
}
