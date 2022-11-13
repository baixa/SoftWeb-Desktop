package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;

/**
 * An entity class containing information about application installers for various operating systems.
 *
 * The class contains an identifier, an installer, additional information about the installer (its size and version)
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Entity
@Table(name = "installer")
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Installer {

    /**
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Related application
     */
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    /**
     * Supported operating system
     */
    @ManyToOne
    @JoinColumn(name = "system_id")
    private OperatingSystem system;

    /**
     * Installer URL
     */
    @Column(name = "path")
    private String installerPath;

    /**
     * Installer Version
     */
    @Column(name = "version")
    private String version;

    /**
     * Installer size (in bytes)
     */
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
