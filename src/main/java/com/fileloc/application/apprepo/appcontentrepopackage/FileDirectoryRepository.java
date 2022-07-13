package com.fileloc.application.apprepo.appcontentrepopackage;

import com.fileloc.application.domain.components.Directory;
import com.fileloc.application.domain.content.FileDirectory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDirectoryRepository extends JpaRepository<FileDirectory,Directory> {
 Optional<FileDirectory> findFileDirectoryByFileLocation(Directory directory);
}
