package com.fileloc.application.domain.components;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Data
public class Directory {
    @NotBlank
    private String rootDir;
    private String subDir;

}
