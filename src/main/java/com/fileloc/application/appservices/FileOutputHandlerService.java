package com.fileloc.application.appservices;

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
    @Override
    public FileOutputStream fileOutput(File file) {

            try {
                FileOutputStream fileOutputStream =new FileOutputStream(file);
                return fileOutputStream;
            }catch (FileNotFoundException fileNotFoundException){
                log.info("File {} could not be found.",file.getName());
            }finally {
                fileSystemDataSourceRelation.persistFileInformationToStorage(file);
            }


        return null;
    }
}