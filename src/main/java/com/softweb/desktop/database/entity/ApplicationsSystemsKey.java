package com.softweb.desktop.database.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ApplicationsSystemsKey implements Serializable {
    @Column(name="application_id")
    private Long applicationId;

    @Column(name = "system_id")
    private Long systemId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApplicationsSystemsKey that = (ApplicationsSystemsKey) o;

        if (!Objects.equals(applicationId, that.applicationId)) return false;
        return Objects.equals(systemId, that.systemId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(applicationId);
        result = 31 * result + (Objects.hashCode(systemId));
        return result;
    }
}
