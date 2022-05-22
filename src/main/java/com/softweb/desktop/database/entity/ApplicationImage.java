package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "applications_images")
@AllArgsConstructor
public class ApplicationImage {

    @Id
    @Column(name = "Path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "Application_ID", nullable = false)
    private Application application;

    public ApplicationImage() {

    }
}
