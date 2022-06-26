package com.softweb.desktop.database.entity;

import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;

/**
 * Entity class Application Images contains data of images, what are shows application UI
 *
 * It has generated id, image and referenced application
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
     * Autogenerated ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Image path contains URL of application image.
     */
    @Column(name = "path")
    private String path;

    /**
     * Transient field which is used to loads application image from URL
     *
     * @see this#path
     */
    @Transient
    private Image image;


    /**
     * Referenced application
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
