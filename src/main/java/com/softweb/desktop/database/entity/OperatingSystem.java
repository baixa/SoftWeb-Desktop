package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class Operating System contains data of OS, that program supports installation on.
 *
 * It has generated id and full name of OS.
 *
 * Also Application class has field, which are referenced with other entity class
 * (installers, that supports installation on this OS).
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "operating_system")
@AllArgsConstructor
public class OperatingSystem {

    /**
     * Autogenerated ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Full name of Operating System
     */
    @Column(name = "name")
    private String name;

    /**
     * List of installers, that allows installation on this OS
     */
    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER)
    private Set<Installer> installers;

    @Override
    public String toString() {
        return "OperatingSystem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
