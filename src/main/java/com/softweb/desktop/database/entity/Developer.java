package com.softweb.desktop.database.entity;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Класс-сущность Developer содержит данные о разработчиках приложения, которые размещают продукты в системе.
 *  *
 *  * Класс содержит индентификатор и данные для авторизации пользователя в системе.
 *  * Также класс содержит ссылку на опубликованные в ситсеме приложения.
 *
 * @author Maksimchuk I.
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "developer")
@AllArgsConstructor
@Transactional
public class Developer {

    /**
     * Automatically generated identifier
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Developer Login
     */
    @Column(name = "username")
    private String username;

    /**
     * Developer name
     */
    @Column(name = "fullname")
    private String fullName;

    /**
     * Developer password
     */
    @Column(name = "password")
    private String password;

    /**
     * An administrator status indicator that allows you to manage other users' applications.
     */
    @Column(name = "is_admin")
    private boolean isAdmin;

    /**
     * Last login date.
     */
    @Column(name = "last_entered")
    private Date lastEntered;

    /**
     * List of published applications.
     */
    @OneToMany(mappedBy = "developer", fetch = FetchType.EAGER)
    private Set<Application> applications;

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", is_admin=" + isAdmin +
                ", last_entered=" + lastEntered +
                '}';
    }
}
