package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * An entity class containing information about supported application licenses.
 *
 * The class contains the license code (identifier) and the name of the license.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "license")
@AllArgsConstructor
public class License {

    /**
     * License code (identifier)
     */
    @Id
    @Column(name = "code")
    private String code;

    /**
     * Full license name
     */
    @Column(name = "name")
    private String name;

    /**
     * List of applications using this license
     */
    @OneToMany(mappedBy = "license", fetch = FetchType.EAGER)
    private Set<Application> applications;

    @Override
    public String toString() {
        return "License{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
