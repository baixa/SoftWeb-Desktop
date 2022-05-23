package com.softweb.desktop.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "applications_systems")
@AllArgsConstructor
@Getter
@Setter
@ToString
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApplicationsSystems that = (ApplicationsSystems) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
