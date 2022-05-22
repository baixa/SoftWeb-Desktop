package com.softweb.desktop.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class ApplicationsSystemsKey implements Serializable {
    @Column(name="application_id")
    private Long applicationId;

    @Column(name = "system_id")
    private Long systemId;

    public ApplicationsSystemsKey() {

    }
}
