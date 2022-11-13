package com.softweb.desktop.database.entity;

import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;

/**
 * An entity class that contains information about the associated application's images.
 *
 * The class contains an automatically generated identifier, an image, and a link to the associated application.
 *
 * @author Maksimchuk I.
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
     * Automatically generated identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * App Image URL
     */
    @Column(name = "path")
    private String path;

    /**
     * App Image
     */
    @Transient
    private Image image;

    /**
     * Related application
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
