package com.fileloc.application.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Entity
@MappedSuperclass
public abstract class ApplicationBaseEntity {

    @Id
    private UUID id = UUID.randomUUID();

}
