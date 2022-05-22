package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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

    @OneToMany(mappedBy = "application")
    private List<ApplicationImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "application")
    private List<ApplicationsSystems> applicationsSystems;

    public Application() {

    }
}
