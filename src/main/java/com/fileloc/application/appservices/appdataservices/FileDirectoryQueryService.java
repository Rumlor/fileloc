package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.domain.content.FileDirectory;
import com.fileloc.application.domain.content.FileEntity;

import java.io.File;
import java.util.List;

public interface FileDirectoryQueryService {
    FileDirectory findRootDirectory();
    boolean deleteFileFromDB(FileEntity file);

    boolean checkIfParentDirectoryExists(File file);
}
