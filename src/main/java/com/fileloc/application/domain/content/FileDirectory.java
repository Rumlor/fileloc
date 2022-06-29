package com.fileloc.application.domain.content;

import com.fileloc.application.domain.ApplicationBaseEntity;
import com.fileloc.application.domain.components.Directory;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class FileDirectory extends ApplicationBaseEntity {


    @NotNull
    private Directory fileLocation;


    @OneToMany(fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "fileDirectory",
            cascade = {CascadeType.ALL}
    )
    @ToString.Exclude
    private List<FileEntity> filesOnDirectory = new ArrayList<>();


}
