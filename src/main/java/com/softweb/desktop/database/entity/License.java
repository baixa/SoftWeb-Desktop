package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс-сущность, содержащий информацию о поддерживаемых лицензиях приложений.
 *
 * Класс содержит код лицензии (индентификатор) и название лицензии.
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
     * Код лицензии (индентификатор)
     */
    @Id
    @Column(name = "code")
    private String code;

    /**
     * Полное название лицензии
     */
    @Column(name = "name")
    private String name;

    /**
     * Список приложений, использующих данную лицензию
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
