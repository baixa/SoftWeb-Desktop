package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "applications_systems")
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
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

    @Override
    public String toString() {
        return "ApplicationsSystems{" +
                "id=" + id +
                ", system=" + system +
                ", installerPath='" + installerPath + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
