package com.softweb.desktop.database.entity;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "developers")
@AllArgsConstructor
@Transactional
public class Developer {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Fullname")
    private String fullName;

    @Column(name = "Password")
    private String password;

    @Column(name = "Admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "developer", fetch = FetchType.EAGER)
    private Set<Application> applications;

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
