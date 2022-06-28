package com.fileloc.application.domain.content;


import com.fileloc.application.domain.ApplicationBaseEntity;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class File extends ApplicationBaseEntity {

    @Basic(optional = false)
    private String fileName;

    @ManyToOne(cascade = CascadeType.MERGE,optional = false)
    private FileDirectory directoryOfFile;

    private boolean isFileLocked;

    @NotBlank
    private String createdUserName;



}
