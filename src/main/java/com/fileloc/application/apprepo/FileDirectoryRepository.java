package com.fileloc.application.apprepo;

import com.fileloc.application.domain.components.Directory;
import com.fileloc.application.domain.content.FileDirectory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDirectoryRepository extends JpaRepository<FileDirectory,Directory> {

}
