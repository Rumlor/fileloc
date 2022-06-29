package com.fileloc.application.appservices;

import com.fileloc.application.applicationconstants.FileSystemConstants;
import com.fileloc.application.appservices.contracts.FileHandling;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FileHandlingService extends AbstractIOService implements FileHandling {

    private  File appFile;

    public FileHandlingService() {

        appFile = new File(FileSystemConstants.ROOT_DIR);
        log.info("File is created in directory {}",appFile.getPath());
    }

    //use this constructor if you will handle object instantiation.
    public FileHandlingService(File file){
        this.appFile=file;
    }


    @Override
    public File getActiveFileFromHandlerService() {
        return isRequestedFilePresent() ? appFile : createFileDirectory(appFile);
    }

    @Override
    public boolean isRequestedFilePresent() {
        return appFile.exists();
    }

    @Override
    public File createFileDirectory(File file) {
        file.mkdir();
        return file;
    }

    @Override
    public void createFile(File file) {
        this.appFile = file;
    }
}
