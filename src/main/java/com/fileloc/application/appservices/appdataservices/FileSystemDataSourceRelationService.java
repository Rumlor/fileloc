package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.apprepo.FileDirectoryRepository;
import com.fileloc.application.apprepo.FileRepository;
import com.fileloc.application.domain.components.Directory;
import com.fileloc.application.domain.content.FileDirectory;
import com.fileloc.application.domain.content.FileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileSystemDataSourceRelationService
        implements FileSystemDataSourceRelation{

    private final FileDirectoryRepository fileDirectoryRepository;
    private final FileRepository fileRepository;


    @Override
    public void persistFileInformationToStorage(File fileToBePersisted) {
        FileEntity file = new FileEntity();
        FileDirectory fileDirectory = new FileDirectory();

        file.setFileLocked(false);
        file.setFileName(fileToBePersisted.getName());
        file.setCreatedUserName("Rumlor");
        file.setFileDirectory(fileDirectory);

        fileDirectory.setFileLocation(Directory.builder()
                .containingDirectory(fileToBePersisted.getParent())
                .fullPath(fileToBePersisted.getPath()).build());

        fileDirectory.setFilesOnDirectory(List.of(file));

        fileDirectoryRepository.save(fileDirectory);
    }
}
