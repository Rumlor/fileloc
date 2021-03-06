package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.apprepo.appcontentrepopackage.FileDirectoryRepository;
import com.fileloc.application.apprepo.appcontentrepopackage.FileRepository;
import com.fileloc.application.appservices.securityservices.SecurityContextAppService;
import com.fileloc.application.domain.components.Directory;
import com.fileloc.application.domain.content.FileDirectory;
import com.fileloc.application.domain.content.FileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileSystemDataSourceRelationService
        implements FileSystemDataSourceRelation{

    private final FileDirectoryRepository fileDirectoryRepository;
    private final FileRepository fileRepository;

    private final SecurityContextAppService securityContextAppService;


    @Override
    public void persistFileInformationToStorage(File fileToBePersisted,long fileLength) {
        log.info("Persisting file with name {} and location {}",
                fileToBePersisted.getName(),fileToBePersisted.getPath());
        FileEntity file = new FileEntity();
        FileDirectory fileDirectory = new FileDirectory();


        file.setFileLocked(false);
        file.setFileName(fileToBePersisted.getName());
        file.setCreatedUserName(securityContextAppService.getAuthenticatedUser().getUserName());
        file.setFileDirectory(fileDirectory);
        String fileSize=null;
        var length = fileLength/1024;
        if(length<1)
            fileSize = String.valueOf(fileLength).concat("B");
        else if (length<=1024)
            fileSize = String.valueOf(length).concat("KB");
        else
            fileSize = String.valueOf(length/1024).concat("MB");
        file.setFileSize(fileSize);
        fileDirectory.setFileLocation(Directory.builder().containingDirectory(fileToBePersisted.getParent()).build());
        fileDirectory.setFilesOnDirectory(Arrays.asList(file));

        fileDirectoryRepository.save(fileDirectory);

    }

    @Override
    public void persistFileToDirectory(File fileToBePersisted,long fileLength) {
        log.info("Parent Directory {} already exists. Merging file {} to directory"
                ,fileToBePersisted.getParent(),fileToBePersisted.getPath());
        FileEntity file = new FileEntity();
        FileDirectory fileDirectory = new FileDirectory();

        var checkFileIfExists = fileRepository.findFileEntityByFileName(fileToBePersisted.getName());
        var filteredFiles = checkFileIfExists.stream().filter(fileEntity ->
                fileEntity.getFileDirectory().getFileLocation()
                        .getContainingDirectory().equals(fileToBePersisted.getParent())).collect(Collectors.toList());
        if(!filteredFiles.isEmpty())
        {
            log.info("File {} already exists in system.Overriding existing one",fileToBePersisted.getPath());
            file = filteredFiles.get(0);
            // it's timestamp has to be updated manually.
            file.setUpdateTime(LocalDateTime.now());

        }else {
            file.setFileLocked(false);
            file.setFileName(fileToBePersisted.getName());
            file.setCreatedUserName(securityContextAppService.getAuthenticatedUser().getUserName());
            file.setFileDirectory(fileDirectory);
            String fileSize=null;
            var length = fileLength/1024;
            if(length<1)
                fileSize = String.valueOf(fileLength).concat("B");
            else if (length<=1024)
                fileSize = String.valueOf(length).concat("KB");
            else
                fileSize = String.valueOf(length/1024).concat("MB");
            file.setFileSize(fileSize);
            fileDirectory.setFileLocation(Directory.builder().containingDirectory(fileToBePersisted.getParent()).build());
            fileDirectory.setFilesOnDirectory(Arrays.asList(file));
        }

        fileRepository.save(file);
    }
}
