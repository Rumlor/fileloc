package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.apprepo.appcontentrepopackage.FileRepository;
import com.fileloc.application.domain.content.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileQueryingServiceImpl implements FileQueryingService{

    @Autowired
    private FileRepository fileEntityRepository;


    @Override
    public List<FileEntity> findAllFilesFromDB() {
        return fileEntityRepository.findAll();
    }

    @Override
    public FileEntity findFileFromPath(String path) {

        return null;
    }
}
