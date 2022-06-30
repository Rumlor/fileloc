package com.fileloc.application.appservices.contracts;

import com.fileloc.application.domain.content.FileEntity;

import java.io.File;
import java.io.FileInputStream;

public interface FileInputHandler {
    FileInputStream fileInput(FileEntity filePath);
}
