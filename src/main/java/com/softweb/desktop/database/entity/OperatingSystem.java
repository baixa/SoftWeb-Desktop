package com.softweb.desktop.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "operating_system")
@AllArgsConstructor
public class OperatingSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER)
    private Set<Installer> applicationsSystems;

    @Override
    public String toString() {
        return "OperatingSystem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
