package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "applications")
@AllArgsConstructor
public class Application{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Short_description")
    private String shortDescription;

    @Column(name = "Description")
    private String description;

    @Column(name = "Logo_Path")
    private String logoPath;

    @Column(name = "Last_Update")
    private Date lastUpdate;

    @Column(name = "License")
    private String license;

    @ManyToOne
    @JoinColumn(name = "Developer_ID")
    private Developer developer;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private List<ApplicationImage> images;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    private Set<ApplicationsSystems> applicationsSystems;

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", license='" + license + '\'' +
                ", developer=" + developer +
                '}';
    }
}
