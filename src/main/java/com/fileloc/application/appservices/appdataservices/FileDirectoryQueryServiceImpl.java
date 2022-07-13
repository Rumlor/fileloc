package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.applicationconstants.appcontentconstants.FileSystemConstants;
import com.fileloc.application.apprepo.appcontentrepopackage.FileDirectoryRepository;
import com.fileloc.application.domain.components.Directory;
import com.fileloc.application.domain.content.FileDirectory;
import com.fileloc.application.domain.content.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileDirectoryQueryServiceImpl implements FileDirectoryQueryService{

    @Autowired
    private FileDirectoryRepository fileDirectoryRepository;


    @Override
    public FileDirectory findRootDirectory() {
        return fileDirectoryRepository.findById(
                Directory.builder()
                        .containingDirectory(FileSystemConstants.ROOT_DIR).build()).orElseGet(()->null);
    }

    @Override
    public boolean deleteFileFromDB(FileEntity fileEntity) {
        FileDirectory directory = fileEntity.getFileDirectory();
        directory.getFilesOnDirectory().remove(fileEntity);
        fileDirectoryRepository.save(directory);
        return true;
    }

    @Override
    public boolean checkIfParentDirectoryExists(File file) {
        return fileDirectoryRepository
                .findFileDirectoryByFileLocation(Directory.builder().containingDirectory(file.getParent()).build()).isPresent();

    }
}
