package com.fileloc.application.views;

import com.fileloc.application.appservices.appdataservices.UI_AppServices_Bridge;
import com.fileloc.application.appservices.appdataservices.UI_AppServices_Bridge_Impl;
import com.fileloc.application.domain.content.FileEntity;
import com.fileloc.application.views.mainpage.MainWebPage;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class UIEventHandler {

    private final UI_AppServices_Bridge ui_appServices_bridge;
    public  void deleteEvent(ComponentEvent<?> event, FileEntity file, MainWebPage webPage){
        if(Objects.isNull(file))
        log.info("File to be deleted is null");
        ui_appServices_bridge.fileDirectoryHandlingServicesDelegate().deleteFileFromSystem(file);
        ui_appServices_bridge.fileDirectoryDatabaseServicesDelegate().deleteFileFromDB(file);
        webPage.fillFileListWithFiles();
    }
}
