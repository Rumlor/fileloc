package com.fileloc.application.appservices;

import com.fileloc.application.appservices.contracts.FileInputHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
@Slf4j
public class FileInputHandlerService implements FileInputHandler {
    @Override
    public FileInputStream fileInput(File file) {
        try {
           FileInputStream fileInputStream =new FileInputStream(file);
           return fileInputStream;

        }catch (FileNotFoundException e){
            log.error("File with name {} is not found",file.getName());
        }
        return null;
    }
}
