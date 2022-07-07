package com.fileloc.application.appservices.appdataservices;

import com.fileloc.application.appservices.contracts.FileHandling;

public interface UI_AppServices_Bridge {
    FileDirectoryQueryService fileDirectoryDatabaseServicesDelegate();
    FileHandling fileDirectoryHandlingServicesDelegate();

}
