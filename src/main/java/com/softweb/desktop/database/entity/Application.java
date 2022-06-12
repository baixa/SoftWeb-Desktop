package com.softweb.desktop.database.entity;

import com.softweb.desktop.auth.Authorization;
import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "application")
@AllArgsConstructor
public class Application{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "description")
    private String description;

    @Column(name = "logo_path")
    private String logoPath;

    @Transient
    private Image logo;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "downloads")
    private int downloads;

    @Column(name = "views")
    private int views;

    @ManyToOne
    @JoinColumn(name = "license")
    private License license;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<ApplicationImage> images;

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

    public void fillStarterInformation() {
        images = new HashSet<>();
        installers = new HashSet<>();
        lastUpdate = new Date();
        developer = Authorization.getCurrentUser();
    }

    public void download() {
        downloads += 1;
    }
    public void view() {
        views += 1;
    }
}
