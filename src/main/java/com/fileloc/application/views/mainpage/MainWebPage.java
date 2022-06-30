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
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.File;

@PageTitle("FileL@ck")
@Route(value = "")
public class MainWebPage extends VerticalLayout {


    private FileUploadComponent fileUploadComponent;

    private FileDownloadComponent fileDownloadComponent;
    private FileHandling fileHandlerService;
    private FileOutputHandler fileOutputHandlerService;

    private FileInputHandler fileInputHandlerService;

    private final FileQueryingService fileQueryingService;
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

        //grid fileList is configured
        configureFileGrid();
        fillFileListWithFiles();

        //create or get file from service.
        fileUploadComponent = new FileUploadComponent(fileSystemManagerUtility(),fileOutputHandlerService,this);
        add(fileList,fileUploadComponent);
    }

    private  File fileSystemManagerUtility(){
       return fileHandlerService.getActiveFileFromHandlerService();
    }




  /**Nested Helper class to handle ui configurations. sole purpose of nesting this class is method grouping.**/
    private static class UIPlumber {

        private <T extends HasSize> void uiMainPageStyling(T component) {
            UIComponentGenericStyler.setComponentSizeFull(component);
        }

    }

    private void configureFileGrid(){
        fileList.setColumns("fileName","createdUserName");
        fileList.addColumn(column->Boolean.valueOf(column.isFileLocked()).toString()).setHeader("File Locked?");
        fileList.addColumn(column->column.getCreationTime().toString()).setHeader("Creation Time");
        fileList.addColumn(column->column.getFileDirectory().getFileLocation().getFullPath()).setHeader("File Path");
        fileList.getColumns().forEach(fileEntityColumn -> fileEntityColumn.setAutoWidth(true));
        //grid select listener.
        fileList.asSingleSelect().addValueChangeListener(new UIListener().fileListCellSelectListener());
    }

    public void fillFileListWithFiles(){
        fileList.setItems(fileQueryingService.findAllFilesFromDB());
    }

    private  class UIListener{

      private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent< Grid<FileEntity>,FileEntity> > fileListCellSelectListener(){

          return selectedFile->{
              String fileFullPath = selectedFile.getValue().getFileDirectory().getFileLocation().getFullPath();
              final File localFile = new File(fileFullPath);
              fileInputHandlerService.fileInput(localFile);
          };
      }



    }




}
