package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "applications_systems")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationsSystems {
    @EmbeddedId
    private ApplicationsSystemsKey id;

    @ManyToOne
    @MapsId("applicationId")
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @MapsId("systemId")
    @JoinColumn(name = "system_id")
    private OperationSystem system;

    @Column(name = "Installer_Path")
    private String installerPath;

    @Column(name = "Version")
    private String version;
}
