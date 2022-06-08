package com.softweb.desktop.database.entity;

import javafx.scene.image.Image;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "application_image")
@AllArgsConstructor
public class ApplicationImage {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "path")
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
