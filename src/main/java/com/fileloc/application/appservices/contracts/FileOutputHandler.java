package com.fileloc.application.appservices.contracts;

import java.io.File;
import java.io.FileOutputStream;

public interface FileOutputHandler {
    FileOutputStream fileOutput(File file);
    void fileOutputToDb(String fileName);
}
