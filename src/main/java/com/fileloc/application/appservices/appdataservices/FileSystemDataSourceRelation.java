package com.fileloc.application.appservices.appdataservices;

import java.io.File;

public interface FileSystemDataSourceRelation {

    void persistFileInformationToStorage(File fileToBePersisted,long fileLength);
    void persistFileToDirectory(File fileToBePersisted,long fileLength);
}
