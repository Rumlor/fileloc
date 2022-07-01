package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.applicationconstants.FileSystemConstants;
import com.fileloc.application.apprepo.FileDirectoryRepository;
import com.fileloc.application.domain.components.Directory;
import com.fileloc.application.domain.content.FileDirectory;
import com.fileloc.application.domain.content.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileDirectoryQueryServiceImpl implements FileDirectoryQueryService{

    @Autowired
    private FileDirectoryRepository fileDirectoryRepository;


    @Override
    public FileDirectory findRootDirectory() {
        return fileDirectoryRepository.findById(
                Directory.builder()
                        .containingDirectory(FileSystemConstants.ROOT_DIR).build()).get();
    }

    @Override
    public boolean deleteFileDirectoryFromFile(FileEntity fileEntity) {

        FileDirectory directory = fileEntity.getFileDirectory();
        directory.getFilesOnDirectory().remove(fileEntity);
        fileDirectoryRepository.save(directory);
        return true;
    }
}
