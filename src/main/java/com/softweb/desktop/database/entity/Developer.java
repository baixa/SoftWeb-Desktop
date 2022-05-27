package com.softweb.desktop.database.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "developers")
@AllArgsConstructor
@Transactional
public class Developer {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_Admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "developer", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Application> applications;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Developer developer = (Developer) o;

        return Objects.equals(id, developer.id);
    }

    @Override
    public int hashCode() {
        return 1551116209;
    }
}
