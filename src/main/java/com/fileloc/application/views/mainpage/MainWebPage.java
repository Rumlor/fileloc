package com.fileloc.application.views.mainpage;

import com.fileloc.application.applicationconstants.FileSystemConstants;
import com.fileloc.application.appservices.appdataservices.FileQueryingService;
import com.fileloc.application.appservices.contracts.FileHandling;
import com.fileloc.application.appservices.contracts.FileInputHandler;
import com.fileloc.application.appservices.contracts.FileOutputHandler;
import com.fileloc.application.domain.content.FileEntity;
import com.fileloc.application.views.UIComponentGenericStyler;
import com.fileloc.application.views.downloadsection.FileDownloadComponent;
import com.fileloc.application.views.uploadsection.FileUploadComponent;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinServletService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.frontend.installer.DefaultFileDownloader;
import org.apache.catalina.webresources.FileResource;
import org.apache.commons.io.FileUtils;
import org.aspectj.weaver.StandardAnnotation;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@PageTitle("FileL@ck")
@Route(value = "")
public class MainWebPage extends VerticalLayout {

    private Anchor anchor;
    private FileUploadComponent fileUploadComponent;

    private FileDownloadComponent fileDownloadComponent;
    private FileHandling fileHandlerService;
    private FileOutputHandler fileOutputHandlerService;

    private FileInputHandler fileInputHandlerService;

    private final FileQueryingService fileQueryingService;

    private  GridContextMenu<FileEntity> fileListContext;
    private static final UIPlumber uiPlumber = new UIPlumber();

    private Grid<FileEntity> fileList = new Grid<>(FileEntity.class);


    public MainWebPage(FileHandling fileHandlerService,
                       FileOutputHandler fileOutputHandlerService,
                       FileQueryingService fileQueryingService,
                       FileInputHandler fileInputHandlerService) {

        //ui configuration
        uiPlumber.uiMainPageStyling(this);
        uiPlumber.uiMainPageStyling(fileList);

        //file handler servis instances are set here.
        this.fileHandlerService = fileHandlerService;
        this.fileOutputHandlerService = fileOutputHandlerService;
        this.fileInputHandlerService = fileInputHandlerService;
        this.fileQueryingService = fileQueryingService;
        this.fileUploadComponent = new FileUploadComponent(fileSystemManagerUtility(),fileOutputHandlerService,this);
        //grid fileList is configured
        configureFileGrid();
        configureFileUpload();
        configureContextMenu();
        fillFileListWithFiles();

        //create or get file from service.

        add(fileList,fileUploadComponent);
    }

    /**FILE UPLOAD COMPONENT**/
    private void configureFileUpload() {
        fileUploadComponent.setSizeFull();
    }

    /**CONTEXT MENU.**/
    private void configureContextMenu() {
        fileListContext = fileList.addContextMenu();
        fileListContext.setOpenOnClick(true);
        fileListContext.addItem("Download",new UIListener().fileDownloadRequestListener());
    }

    /**GET ACTIVE FILE DIR.**/
    private  File fileSystemManagerUtility(){
       return fileHandlerService.getActiveFileFromHandlerService();
    }

    /**FILE GRID CONFIG.**/
    private void configureFileGrid(){
        fileList.setColumns("fileName","createdUserName");
        fileList.addColumn(column->Boolean.valueOf(column.isFileLocked()).toString()).setHeader("File Locked?");
        fileList.addColumn(column->column.getCreationTime().toString()).setHeader("Creation Time");
        fileList.addColumn(column->column.getFileDirectory().getFileLocation().getContainingDirectory()).setHeader("File Path");
        fileList.addColumn(column->column.getFileSize()).setHeader("File Size");
        fileList.getColumns().forEach(fileEntityColumn -> fileEntityColumn.setAutoWidth(true));
    }

    /**LOAD FILES FROM DB.**/
    public void fillFileListWithFiles(){
        fileList.setItems(fileQueryingService.findAllFilesFromDB());
    }



    /**UI EVENTS.**/
    private class UIListener{
        private  ComponentEventListener <GridContextMenu.GridContextMenuItemClickEvent<FileEntity>>  fileDownloadRequestListener(){

          return selectedFile-> {


          };
        }


    }

    /**UI VISUAL CONFIG.**/
    private static class UIPlumber {

        private <T extends HasSize> void uiMainPageStyling(T component) {
            UIComponentGenericStyler.setComponentSizeFull(component);
        }

    }





}
