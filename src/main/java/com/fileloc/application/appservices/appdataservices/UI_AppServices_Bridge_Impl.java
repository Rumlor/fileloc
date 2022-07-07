package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.appservices.FileHandlingService;
import com.fileloc.application.appservices.contracts.FileHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UI_AppServices_Bridge_Impl
        implements UI_AppServices_Bridge{



    private static FileDirectoryQueryService fileDirectoryQueryService;
    private static  FileHandling fileHandlingService;


    @Autowired
    public UI_AppServices_Bridge_Impl(FileDirectoryQueryService fileDirectoryQueryService,FileHandling fileHandlingService) {
    UI_AppServices_Bridge_Impl.fileDirectoryQueryService =fileDirectoryQueryService;
    UI_AppServices_Bridge_Impl.fileHandlingService = fileHandlingService;
    }

    @Override
    public FileDirectoryQueryService fileDirectoryDatabaseServicesDelegate() {
        return fileDirectoryQueryService;
    }

    @Override
    public FileHandling fileDirectoryHandlingServicesDelegate() {
        return fileHandlingService;
    }
}
