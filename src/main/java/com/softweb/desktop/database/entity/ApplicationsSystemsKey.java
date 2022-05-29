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
@RequiredArgsConstructor
@AllArgsConstructor
public class ApplicationsSystemsKey implements Serializable {
    @Column(name="application_id")
    private Long applicationId;

    @Column(name = "system_id")
    private Long systemId;

    @Override
    public String toString() {
        return "ApplicationsSystemsKey{" +
                "applicationId=" + applicationId +
                ", systemId=" + systemId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationsSystemsKey that = (ApplicationsSystemsKey) o;
        return applicationId.equals(that.applicationId) && systemId.equals(that.systemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationId, systemId);
    }
}
