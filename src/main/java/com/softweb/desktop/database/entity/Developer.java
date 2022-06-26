package com.softweb.desktop.database.entity;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Класс-сущность Developer содержит данные о разработчиках приложения, которые размещают продукты в системе.
 *
 * Класс содержит индентификатор и данные для авторизации пользователя в системе.
 * Также класс содержит ссылку на опубликованные в ситсеме приложения.
 *
 * @author Максимчук И.
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
     * Автоматически генерируемый индентификатор
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Логин разработчика
     */
    @Column(name = "username")
    private String username;

    /**
     * Имя разработчика
     */
    @Column(name = "fullname")
    private String fullName;

    /**
     * Пароль разработчика
     */
    @Column(name = "password")
    private String password;

    /**
     * Индикатор статуса администратора, позволяющего управлять приложениями других пользователей.
     */
    @Column(name = "is_admin")
    private boolean isAdmin;

    /**
     * Дата последнего входа в систему.
     */
    @Column(name = "last_entered")
    private Date lastEntered;

    /**
     * Список опубликованных приложений.
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
