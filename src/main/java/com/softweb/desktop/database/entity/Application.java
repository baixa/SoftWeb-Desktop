package com.softweb.desktop.database.entity;

import com.softweb.desktop.auth.Authorization;
import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;
import java.util.*;


/**
 * The Application entity class contains application data.
 *
 * The class contains an automatically generated identifier, the name of the application,
 * its description and title, the path and image of the application logo, license information, and application analytics data: the number of downloads and views.
 *
 * The class also contains fields that associate the application with its developer, image list, and application installers.
 *
 * @author Maksimchuk I.
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
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * App name
     */
    @Column(name = "name")
    private String name;

    /**
     * Application title
     */
    @Column(name = "short_description")
    private String shortDescription;

    /**
     * Application Description
     */
    @Column(name = "description")
    private String description;

    /**
     * Application logo image URL
     */
    @Column(name = "logo_path")
    private String logoPath;

    /**
     * Logo image
     *
     * @see this#logoPath
     */
    @Transient
    private Image logo;

    /**
     * The date the app was last updated
     */
    @Column(name = "last_update")
    private Date lastUpdate;

    /**
     * Number of app downloads
     */
    @Column(name = "downloads")
    private int downloads;

    /**
     * App Views
     */
    @Column(name = "views")
    private int views;

    /**
     * Application license
     */
    @ManyToOne
    @JoinColumn(name = "license")
    private License license;

    /**
     * Application developer
     */
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    /**
     * Application image list
     */
    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<ApplicationImage> images;

    /**
     * List of app installers
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
     * The method increases the number of app downloads
     */
    public void increaseDownloadsCounter() {
        downloads += 1;
    }

    /**
     * The method increases the number of app views
     */
    public void increaseViewsCounter() {
        views += 1;
    }
}
