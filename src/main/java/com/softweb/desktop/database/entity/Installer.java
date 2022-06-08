package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "installer")
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Installer {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "system_id")
    private OperatingSystem system;

    @Column(name = "path")
    private String installerPath;

    @Column(name = "version")
    private String version;

    @Column(name = "size")
    private int size;

    @Override
    public String toString() {
        return "ApplicationsSystems{" +
                "id=" + id +
                ", system=" + system +
                ", installerPath='" + installerPath + '\'' +
                ", version='" + version + '\'' +
                ", size=" + size +
                '}';
    }
}
