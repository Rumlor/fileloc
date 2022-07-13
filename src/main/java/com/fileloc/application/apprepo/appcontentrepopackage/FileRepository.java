package com.fileloc.application.apprepo.appcontentrepopackage;

import com.fileloc.application.apprepo.ApplicationBaseRepository;
import com.fileloc.application.domain.content.FileEntity;

import java.util.List;

public interface FileRepository extends ApplicationBaseRepository<FileEntity> {
    List<FileEntity> findFileEntityByFileName(String fileName);
}
