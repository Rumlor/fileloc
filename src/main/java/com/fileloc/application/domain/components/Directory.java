package com.fileloc.application.domain.components;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class Directory {
    @NotBlank
    private String containingDirectory;
    private String fullPath;


    public Directory() {

    }
}
