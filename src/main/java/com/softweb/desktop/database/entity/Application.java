package com.softweb.desktop.database.entity;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
                ", license='" + license + '\'' +
                ", developer=" + developer +
                '}';
    }
}
