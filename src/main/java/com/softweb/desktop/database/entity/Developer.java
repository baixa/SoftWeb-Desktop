package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "developers")
@AllArgsConstructor
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

    @OneToMany(mappedBy = "developer")
    private List<Application> applications = new ArrayList<>();

    public Developer() {

    }
}
