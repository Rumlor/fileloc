package com.fileloc.application.appservices;

import com.fileloc.application.appservices.contracts.FileInputHandler;
import com.fileloc.application.domain.content.FileEntity;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
@Slf4j
public class FileInputHandlerService implements FileInputHandler {

    @Override
    public FileInputStream fileInput(FileEntity file) {

        FileInputStream fileInputStream = null;
        try {
            File fileObject = new File(file.getFileDirectory().getFileLocation().getContainingDirectory().concat("\\").concat(file.getFileName()));
            fileInputStream =new FileInputStream(fileObject);
        }catch (FileNotFoundException e){
            log.error("File with name {} is not found",file.getFileName());
        }
        return fileInputStream;
    }
}
