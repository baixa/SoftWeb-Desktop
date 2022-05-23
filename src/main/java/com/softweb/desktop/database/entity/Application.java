package com.softweb.desktop.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "applications")
@AllArgsConstructor
public class Application{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Short_description")
    private String shortDescription;

    @Column(name = "Description")
    private String description;

    @Column(name = "Logo_Path")
    private String logoPath;

    @Column(name = "Last_Update")
    private Date lastUpdate;

    @Column(name = "License")
    private String license;

    @ManyToOne
    @JoinColumn(name = "Developer_ID")
    private Developer developer;

    @OneToMany(mappedBy = "application")
    @ToString.Exclude
    private List<ApplicationImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "application")
    @ToString.Exclude
    private List<ApplicationsSystems> applicationsSystems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Application that = (Application) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 85634910;
    }
}
