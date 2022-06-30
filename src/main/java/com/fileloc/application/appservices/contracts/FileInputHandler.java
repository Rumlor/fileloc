package com.fileloc.application.appservices.contracts;

import java.io.File;
import java.io.FileInputStream;

public interface FileInputHandler {
    FileInputStream fileInput(File file);
}
