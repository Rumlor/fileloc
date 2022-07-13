package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.appservices.FileHandlingService;
import com.fileloc.application.appservices.contracts.FileHandling;
import com.fileloc.application.appservices.contracts.FileInputHandler;
import com.fileloc.application.appservices.securityservices.AppRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UI_AppServices_Bridge_Impl
        implements UI_AppServices_Bridge{



    private static FileDirectoryQueryService fileDirectoryQueryService;
    private static  FileHandling fileHandlingService;

    private static FileInputHandler fileInputHandler;

    private AppRegistrationService registrationService;

    @Autowired
    public UI_AppServices_Bridge_Impl(FileDirectoryQueryService fileDirectoryQueryService,FileHandling fileHandlingService,FileInputHandler handler,AppRegistrationService registrationService) {
    UI_AppServices_Bridge_Impl.fileDirectoryQueryService =fileDirectoryQueryService;
    UI_AppServices_Bridge_Impl.fileHandlingService = fileHandlingService;
    UI_AppServices_Bridge_Impl.fileInputHandler = handler;
    this.registrationService = registrationService;
    }

    @Override
    public FileDirectoryQueryService fileDirectoryDatabaseServicesDelegate() {
        return fileDirectoryQueryService;
    }

    @Override
    public FileHandling fileDirectoryHandlingServicesDelegate() {
        return fileHandlingService;
    }

    @Override
    public FileInputHandler fileInputHandler() {
        return fileInputHandler;
    }

    @Override
    public AppRegistrationService registrationServiceDelegate() {
        return registrationService;
    }
}
