package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity class Licenses contains data of application's licenses.
 *
 * It has short code of license (identifier) and it name.
 *
 * Also Application class has field, which are referenced with other entity class
 * (applications, that are used this license).
 *
 * @author Максимчук И.
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
     * Short code of license, that identifies license
     */
    @Id
    @Column(name = "code")
    private String code;

    /**
     * Full name of license
     */
    @Column(name = "name")
    private String name;

    /**
     * List of applications, that are used this license
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
