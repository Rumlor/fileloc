package com.fileloc.application.views;

import com.fileloc.application.appservices.appdataservices.UI_AppServices_Bridge;
import com.fileloc.application.domain.content.FileEntity;
import com.fileloc.application.views.mainpage.MainWebPage;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.server.StreamResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
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

    public void downloadEvent(FileEntity file , HasComponents component){

        log.info("file to be downloaded is {}",file.getFileName());
            StreamResource resource = new StreamResource(file.getFileName(),()->ui_appServices_bridge.fileInputHandler().fileInput(file));
            Anchor anchor = new Anchor(resource,"a");
            Element anchorElement = anchor.getElement();
            anchorElement.setAttribute("download",true);
            anchorElement.getStyle().set("display","none");
            component.add(anchor);
            anchorElement.executeJs("return new Promise(resolve =>{this.click(); setTimeout(() => resolve(true), 150)})", anchorElement).then(jsonValue -> component.remove(anchor));

    }
}
