package com.fileloc.application.appservices.contracts;

import java.io.File;

public interface FileHandling {
    File getActiveFileFromHandlerService();
    boolean isRequestedFilePresent();
    File createFileDirectory(File file);

    void createFile(File file);
}
