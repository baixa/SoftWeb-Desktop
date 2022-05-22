package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "operation_systems")
@AllArgsConstructor
public class OperationSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "system")
    private List<ApplicationsSystems> applicationsSystems;

    public OperationSystem() {

    }
}
