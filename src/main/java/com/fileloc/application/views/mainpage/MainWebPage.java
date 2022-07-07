package com.fileloc.application.views.mainpage;

import com.fileloc.application.appservices.appdataservices.FileDirectoryQueryService;
import com.fileloc.application.appservices.appdataservices.FileQueryingService;
import com.fileloc.application.appservices.contracts.FileHandling;
import com.fileloc.application.appservices.contracts.FileInputHandler;
import com.fileloc.application.appservices.contracts.FileOutputHandler;
import com.fileloc.application.domain.content.FileEntity;
import com.fileloc.application.views.UIComponentGenericStyler;
import com.fileloc.application.views.UIEventHandler;
import com.fileloc.application.views.downloadsection.OptionsComponent;
import com.fileloc.application.views.uploadsection.FileUploadComponent;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@PageTitle("FileL@ck")
@Route(value = "")
@Slf4j
public class MainWebPage extends VerticalLayout {

    private FileUploadComponent fileUploadComponent;

    private OptionsComponent optionsComponent;

    private FileHandling fileHandlerService;
    private FileOutputHandler fileOutputHandlerService;

    private FileInputHandler fileInputHandlerService;

    private final FileQueryingService fileQueryingService;
    private final FileDirectoryQueryService fileDirectoryQueryService;

    private static final UIPlumber uiPlumber = new UIPlumber();
    private Grid<FileEntity> fileList = new Grid<>(FileEntity.class);


    public MainWebPage(FileHandling fileHandlerService,
                       FileOutputHandler fileOutputHandlerService,
                       FileQueryingService fileQueryingService,
                       FileInputHandler fileInputHandlerService,
                       FileDirectoryQueryService fileDirectoryQueryService,
                       UIEventHandler uiEventHandler) {

        //ui configuration
        uiPlumber.uiMainPageStyling(this);
        uiPlumber.uiMainPageStyling(fileList);

        //file handler servis instances are set here.
        this.fileHandlerService = fileHandlerService;
        this.fileOutputHandlerService = fileOutputHandlerService;
        this.fileInputHandlerService = fileInputHandlerService;
        this.fileQueryingService = fileQueryingService;
        this.fileDirectoryQueryService = fileDirectoryQueryService;
        this.fileUploadComponent = new FileUploadComponent(fileSystemManagerUtility(),fileOutputHandlerService,this);
        this.optionsComponent = new OptionsComponent(fileInputHandlerService,this,uiEventHandler);
        //grid fileList is configured

        configureFileGrid();
        var gridAndOptions = configureFileGridAndOptionsComponent();
        configureFileUpload();
        fillFileListWithFiles();


        add(gridAndOptions,fileUploadComponent);
    }

    private HorizontalLayout configureFileGridAndOptionsComponent() {
        HorizontalLayout layout = new HorizontalLayout(fileList,optionsComponent);
        layout.setFlexGrow(3,fileList);
        layout.setFlexGrow(1,optionsComponent);
        return layout;
    }


    /**FILE UPLOAD COMPONENT**/
    private void configureFileUpload() {
        fileUploadComponent.setSizeFull();
    }


    /**GET ACTIVE FILE DIR.**/
    private  File fileSystemManagerUtility(){
       return fileHandlerService.getActiveFileFromHandlerService();
    }

    /**FILE GRID CONFIG.**/
    private void configureFileGrid(){
        fileList.setColumns("fileName","createdUserName");
        fileList.addColumn(column->Boolean.valueOf(column.isFileLocked()).toString()).setHeader("File Locked?");
        fileList.addColumn(column->column.getUpdateTime().toLocalDate().toString()).setHeader("Creation Time");
        fileList.addColumn(column->column.getFileDirectory().getFileLocation().getContainingDirectory()).setHeader("File Path");
        fileList.addColumn(column->column.getFileSize()).setHeader("File Size");
        fileList.getColumns().forEach(fileEntityColumn -> fileEntityColumn.setAutoWidth(true));
        fileList.addItemClickListener(item->{
            optionsComponent.setVisible(true);
            optionsComponent.fillOptions(item.getItem());
        });
        fileList.setAllRowsVisible(true);

    }











    /**LOAD FILES FROM DB.**/
    public void fillFileListWithFiles(){
        var res = fileDirectoryQueryService.findRootDirectory();
       var condition = res!=null ? fileList.setItems(res.getFilesOnDirectory()) : null;

    }


    /**UI VISUAL CONFIG.**/
    private static class UIPlumber {

        private <T extends HasSize> void uiMainPageStyling(T component) {
            UIComponentGenericStyler.setComponentSizeFull(component);
        }

    }





}
