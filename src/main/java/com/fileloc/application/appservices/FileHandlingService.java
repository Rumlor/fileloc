package com.fileloc.application.appservices;

import com.fileloc.application.applicationconstants.FileSystemConstants;
import com.fileloc.application.appservices.contracts.FileHandling;
import com.fileloc.application.domain.content.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    @Override
    public boolean deleteFileFromSystem(FileEntity file) {

        try {
            var dir = file.getFileDirectory().getFileLocation().getContainingDirectory();
            var filename = file.getFileName();
            Files.deleteIfExists(Path.of(dir,filename));
        }catch (IOException e) {
            log.error("IO exception while deleting file from system.");
            throw new RuntimeException(e);
        }

        return true;
    }
}
