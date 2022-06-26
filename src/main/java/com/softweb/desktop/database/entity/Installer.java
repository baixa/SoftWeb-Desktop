package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Класс-сущность, содержащий информацию об установщиках приложений для различных операционных систем.
 *
 * Класс содержит индентификатор, установщик, дополнительную информацию об установщике (его размер и версию)
 *
 * @author Максимчук И.
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
     * Автоматически генерируемый индентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Связанное приложение
     */
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    /**
     * Поддерживаемая операционная система
     */
    @ManyToOne
    @JoinColumn(name = "system_id")
    private OperatingSystem system;

    /**
     * URL установщика
     */
    @Column(name = "path")
    private String installerPath;

    /**
     * Версия установщика
     */
    @Column(name = "version")
    private String version;

    /**
     * Размер установщика (в байтах)
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
