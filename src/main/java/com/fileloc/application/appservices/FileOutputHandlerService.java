package com.fileloc.application.appservices;

import com.fileloc.application.appservices.appdataservices.FileDirectoryQueryService;
import com.fileloc.application.appservices.appdataservices.FileSystemDataSourceRelation;
import com.fileloc.application.appservices.contracts.FileOutputHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileOutputHandlerService implements FileOutputHandler {

    private final FileSystemDataSourceRelation fileSystemDataSourceRelation;
    private final FileDirectoryQueryService fileDirectoryQueryService;
    @Override
    public FileOutputStream fileOutput(File file) {
        log.info("output stream file {}",file.getPath());
        FileOutputStream fileOutputStream=null;
        long length = file.length();
            try {
                 fileOutputStream = new FileOutputStream(file);
            }catch (FileNotFoundException fileNotFoundException){
                //exception is caught only to be able to log it.
                log.info("File {} could not be found.",file.getName());
                throw new RuntimeException(fileNotFoundException);
            }

        if(!fileDirectoryQueryService.checkIfParentDirectoryExists(file))
            //if file directory is present , merges current file to parent directory
            fileSystemDataSourceRelation.persistFileInformationToStorage(file,length);
        else
            //if file directory is not present , creates new directory and binds file to it.
            fileSystemDataSourceRelation.persistFileToDirectory(file,length);

        return fileOutputStream;
    }
}
