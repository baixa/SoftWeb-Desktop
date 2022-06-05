package com.softweb.desktop.database.entity;

import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "applications_images")
@AllArgsConstructor
public class ApplicationImage {

    @Id
    @Column(name = "Path")
    private String path;

    @Transient
    private Image image;

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
