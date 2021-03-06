package com.fileloc.application.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class ApplicationBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;


}
