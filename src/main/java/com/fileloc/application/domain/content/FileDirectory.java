package com.fileloc.application.domain.content;

import com.fileloc.application.domain.ApplicationBaseEntity;
import com.fileloc.application.domain.components.Directory;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class FileDirectory extends ApplicationBaseEntity {


    @NotNull
    private Directory fileLocation;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @OneToMany(fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "directoryOfFile",
            cascade = CascadeType.REMOVE)
    private List<File> filesOnDirectory = new ArrayList<>();


}
