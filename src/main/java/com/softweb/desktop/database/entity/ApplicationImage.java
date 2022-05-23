package com.softweb.desktop.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "applications_images")
@AllArgsConstructor
public class ApplicationImage {

    @Id
    @Column(name = "Path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "Application_ID", nullable = false)
    private Application application;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApplicationImage that = (ApplicationImage) o;

        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return 2093808656;
    }
}
