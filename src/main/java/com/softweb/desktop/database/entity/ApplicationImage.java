package com.softweb.desktop.database.entity;

import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;

/**
 * Класс-сущность, содержащий информацию об изображениях связанного приложения.
 *
 * Класс содержит автоматически генерируемый индентификатор, изображение и ссылку на связанное приложение.
 *
 * @author Максимчук И.
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "application_image")
@AllArgsConstructor
public class ApplicationImage {

    /**
     * Автоматически генерируемый индентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * URL изображения приложения
     */
    @Column(name = "path")
    private String path;

    /**
     * Изображение приложения
     */
    @Transient
    private Image image;

    /**
     * Связанное приложение
     */
    @ManyToOne
    @JoinColumn(name = "Application_ID", nullable = false)
    private Application application;

    @Override
    public String toString() {
        return "ApplicationImage{" +
                "path='" + path + '\'' +
                ", application=" + application +
                '}';
    }
}
