package com.fileloc.application.domain.components;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;



/**For composite key , additional fields will be added.**/
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class Directory implements Serializable {

    @NotBlank
    private String containingDirectory;



    public Directory() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return Objects.equals(containingDirectory, directory.containingDirectory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(containingDirectory);
    }
}
