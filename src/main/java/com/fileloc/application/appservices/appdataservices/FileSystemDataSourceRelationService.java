package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.apprepo.FileDirectoryRepository;
import com.fileloc.application.apprepo.FileRepository;
import com.fileloc.application.domain.components.Directory;
import com.fileloc.application.domain.content.FileDirectory;
import com.fileloc.application.domain.content.FileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileSystemDataSourceRelationService
        implements FileSystemDataSourceRelation{

    private final FileDirectoryRepository fileDirectoryRepository;
    private final FileRepository fileRepository;

    //default system user is creating files. authentication/authorization will be added later on.
    @Override
    public void persistFileInformationToStorage(File fileToBePersisted) {
        log.info("Persisting file with name {} and location {}",fileToBePersisted.getName(),fileToBePersisted.getPath());
        FileEntity file = new FileEntity();
        FileDirectory fileDirectory = new FileDirectory();

        file.setFileLocked(false);
        file.setFileName(fileToBePersisted.getName());
        file.setCreatedUserName("Rumlor");
        file.setFileDirectory(fileDirectory);
        file.setFileSize(String.valueOf(FileUtils.sizeOf(fileToBePersisted)/1024).concat("KB"));

        fileDirectory.setFileLocation(Directory.builder().containingDirectory(fileToBePersisted.getParent()).build());
        fileDirectory.setFilesOnDirectory(List.of(file));

        fileRepository.save(file);
    }
}
