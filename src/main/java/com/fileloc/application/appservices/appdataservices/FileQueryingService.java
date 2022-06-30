package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.domain.content.FileEntity;

import java.util.List;

public interface FileQueryingService {
    List<FileEntity> findAllFilesFromDB();
    FileEntity findFileFromPath(String path);
}
