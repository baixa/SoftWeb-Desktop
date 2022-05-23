package com.softweb.desktop.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @ToString.Exclude
    private List<ApplicationsSystems> applicationsSystems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OperationSystem that = (OperationSystem) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 590684655;
    }
}
