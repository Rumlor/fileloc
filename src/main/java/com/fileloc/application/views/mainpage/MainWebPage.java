package com.fileloc.application.views.mainpage;

import com.fileloc.application.applicationconstants.FileSystemConstants;
import com.fileloc.application.appservices.appdataservices.FileDirectoryQueryService;
import com.fileloc.application.appservices.appdataservices.FileQueryingService;
import com.fileloc.application.appservices.contracts.FileHandling;
import com.fileloc.application.appservices.contracts.FileInputHandler;
import com.fileloc.application.appservices.contracts.FileOutputHandler;
import com.fileloc.application.domain.content.FileEntity;
import com.fileloc.application.views.UIComponentGenericStyler;
import com.fileloc.application.views.downloadsection.FileDownloadComponent;
import com.fileloc.application.views.uploadsection.FileUploadComponent;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
import org.vaadin.firitin.components.DynamicFileDownloader;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@PageTitle("FileL@ck")
@Route(value = "")
public class MainWebPage extends VerticalLayout {
    private final UIListener uiListener = new UIListener();
    private FileUploadComponent fileUploadComponent;

    private FileHandling fileHandlerService;
    private FileOutputHandler fileOutputHandlerService;

    private FileInputHandler fileInputHandlerService;

    private final FileQueryingService fileQueryingService;
    private final FileDirectoryQueryService fileDirectoryQueryService;

    private  GridContextMenu<FileEntity> fileListContext;
    private static final UIPlumber uiPlumber = new UIPlumber();

    private Grid<FileEntity> fileList = new Grid<>(FileEntity.class);

    private Anchor downloadAnchor ;

    public MainWebPage(FileHandling fileHandlerService,
                       FileOutputHandler fileOutputHandlerService,
                       FileQueryingService fileQueryingService,
                       FileInputHandler fileInputHandlerService,FileDirectoryQueryService fileDirectoryQueryService) {

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
        //grid fileList is configured
        configureFileGrid();
        configureFileUpload();
        configureContextMenu();
        fillFileListWithFiles();
        configureDownloadNotification();
        //create or get file from service.

        add(fileList,fileUploadComponent);
    }

    private void configureDownloadNotification() {

    }

    /**FILE UPLOAD COMPONENT**/
    private void configureFileUpload() {
        fileUploadComponent.setSizeFull();
    }

    /**CONTEXT MENU.**/
    private void configureContextMenu() {
        fileListContext = fileList.addContextMenu();
        fileListContext.setOpenOnClick(true);
        //fileListContext.addItem();
        fileListContext.addItem("Delete",uiListener.fileDeleteRequestListener());
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
        fileList.setItems(fileDirectoryQueryService.findRootDirectory().getFilesOnDirectory());
    }



    /**UI EVENTS.**/
    private class UIListener{
        private  ComponentEventListener <GridContextMenu.GridContextMenuItemClickEvent<FileEntity>>  fileDownloadRequestListener() {

            return selectedFile -> {
                StreamResource resource =
                        new StreamResource(selectedFile.getItem().get().getFileName(),
                                ()->fileInputHandlerService.fileInput(selectedFile.getItem().get()));

            };
        }
        private  ComponentEventListener <GridContextMenu.GridContextMenuItemClickEvent<FileEntity>> fileDeleteRequestListener(){

            return selectedFile->
            {   //DELETE FILE FROM DB
                fileDirectoryQueryService.deleteFileDirectoryFromFile(selectedFile.getItem().get());
                //DELETE FILE FROM FILE SYSTEM
                fileHandlerService.deleteFileFromSystem(selectedFile.getItem().get());
                Notification.show("Successfully Deleted",10000, Notification.Position.MIDDLE);
                fillFileListWithFiles();
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
