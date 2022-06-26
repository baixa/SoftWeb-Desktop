package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс-сущность, содержащий информацию об операционных системах, поддерживаемых в приложении.
 *
 * Класс содержи индентификатор системы и ее полное имя.
 *
 * @author Максимчук И.
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
     * Автоматически генерируемый индентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя операционной системы
     */
    @Column(name = "name")
    private String name;

    /**
     * Список установщиков, предназначенных для данной ОС
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
