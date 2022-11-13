package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * An entity class containing information about the operating systems supported by the application.
 *
 * The class contains the system identifier and its fully qualified name.
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
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Operating system name
     */
    @Column(name = "name")
    private String name;

    /**
     * List of installers intended for this OS
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
