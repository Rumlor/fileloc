package com.fileloc.application.views.mainpage;

import com.fileloc.application.applicationconstants.FileSystemConstants;
import com.fileloc.application.appservices.contracts.FileHandling;
import com.fileloc.application.appservices.contracts.FileOutputHandler;
import com.fileloc.application.views.uploadsection.FileUploadComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.File;

@PageTitle("FileL@ck")
@Route(value = "")
public class MainWebPage extends VerticalLayout {


    private FileUploadComponent fileUploadComponent;
    private FileHandling fileHandlerService;
    private FileOutputHandler fileOutputHandlerService;


    public MainWebPage(FileHandling fileHandlerService,FileOutputHandler fileOutputHandlerService) {
        this.fileHandlerService = fileHandlerService;
        this.fileOutputHandlerService = fileOutputHandlerService;
        //create or get file from service.
        fileUploadComponent = new FileUploadComponent(fileSystemManagerUtility(),fileOutputHandlerService);
        add(fileUploadComponent);
    }

    private  File fileSystemManagerUtility(){
       return fileHandlerService.getActiveFileFromHandlerService();
    }


}
