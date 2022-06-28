package com.fileloc.application.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@MappedSuperclass
public abstract class ApplicationBaseEntity {

    @Id
    private UUID id = UUID.randomUUID();

    @CreationTimestamp
    private LocalDateTime creationTime;

}
