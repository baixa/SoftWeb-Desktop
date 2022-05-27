package com.softweb.desktop.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
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

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<ApplicationImage> images;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<ApplicationsSystems> applicationsSystems;

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

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", license='" + license + '\'' +
                ", developer=" + developer.getUsername() +
                ", images=" + images.stream().map(ApplicationImage::getPath).collect(Collectors.joining(",", "{", "}")) +
                ", applicationsSystems{" + getApplicationSystemString() +
                "}}";
    }

    private String getApplicationSystemString() {
        StringBuilder result = new StringBuilder();
        applicationsSystems.forEach(applicationSystem -> result.append("{")
                                                                .append(applicationSystem.getApplication())
                                                                .append(", ")
                                                                .append(applicationSystem.getSystem())
                                                                .append("}"));
        return result.toString();
    }
}
