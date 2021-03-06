package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.appservices.contracts.FileHandling;
import com.fileloc.application.appservices.contracts.FileInputHandler;
import com.fileloc.application.appservices.securityservices.AppRegistrationService;

public interface UI_AppServices_Bridge {
    FileDirectoryQueryService fileDirectoryDatabaseServicesDelegate();
    FileHandling fileDirectoryHandlingServicesDelegate();
    FileInputHandler fileInputHandler();

    AppRegistrationService registrationServiceDelegate();

}
