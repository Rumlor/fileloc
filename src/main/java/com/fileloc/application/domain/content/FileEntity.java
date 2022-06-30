package com.fileloc.application.domain.content;


import com.fileloc.application.domain.ApplicationBaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class FileEntity extends ApplicationBaseEntity {

    @Basic(optional = false)
    private String fileName;

    @ManyToOne(optional = false,cascade = {CascadeType.MERGE})
    @JoinColumns({
            @JoinColumn(name = "filedir_dir",referencedColumnName = "dir")})
    private FileDirectory fileDirectory;

    private boolean isFileLocked;

    @NotBlank
    private String createdUserName;

    @NotBlank
    private String fileSize;



}
