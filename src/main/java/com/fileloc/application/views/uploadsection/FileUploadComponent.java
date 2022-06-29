package com.fileloc.application.views.uploadsection;

import com.fileloc.application.appservices.contracts.FileOutputHandler;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileUploadComponent extends VerticalLayout {
    private  Upload fileUploader;
    private Span errorField;
    private  UiListener uiListener;

    /**File directory to be populated by upload component.**/
    private File uploadedFileDirectory;
    /**File Output operations handler. (Uploading etc.) **/
    private FileOutputHandler fileOutputHandlerService;

    public FileUploadComponent(File uploadedFileDirectory, FileOutputHandler fileOutputHandlerService) {

        this.fileOutputHandlerService = fileOutputHandlerService;
        this.uploadedFileDirectory = uploadedFileDirectory;
        uiListener = new UiListener();

        setUploaderUIAttributes();
        setErrorFieldAttributes();
        setUploaderUIListeners();

        add(fileUploader,errorField);
    }
    private void setErrorFieldAttributes() {
        errorField = new Span();
        errorField.setVisible(false);
        errorField.getStyle().set("color","red");

    }

    private void setUploaderUIAttributes() {
        fileUploader = new Upload();
        fileUploader.setDropAllowed(true);
        fileUploader.setDropLabel(new Label("Drag and drop file here"));
        fileUploader.setMaxFiles(1);
        //in bytes . equals 512 MBs.
        fileUploader.setMaxFileSize(512*1024*1024);
        //upload listener. extra event listeners will be added.
        fileUploader.setReceiver(uiListener.uploadFileEventListener(uploadedFileDirectory));
    }

    private void setUploaderUIListeners() {
        fileUploader.addFailedListener(uiListener.failedEventComponentEventListener());
        fileUploader.addFileRejectedListener(uiListener.fileRejectedEventComponentEventListener());
        fileUploader.addSucceededListener(uiListener.succeededEventComponentEventListener());
    }


    /** Ui event listener nested helper class **/
    public class UiListener {
      private  ComponentEventListener<FailedEvent> failedEventComponentEventListener(){
            return event -> showErrorMessage(event.getReason().getMessage());
        }
      private  ComponentEventListener<FileRejectedEvent> fileRejectedEventComponentEventListener(){
            return event -> showErrorMessage(event.getErrorMessage());
        }
      private MultiFileReceiver uploadFileEventListener(File uploadFileDirectory){

          return  (String fileName, String mimeType) -> {
              File file = new File(uploadFileDirectory,fileName);
              return fileOutputHandlerService.fileOutput(file);
          };

      }
      private ComponentEventListener<SucceededEvent> succeededEventComponentEventListener(){
        return event -> {
            hideErrorMessage();
            Notification.show(event.getFileName().concat(" Successfully Uploaded"),5000, Notification.Position.MIDDLE);
        };
      }

    }


    private void showErrorMessage(String message){
       errorField.setVisible(true);
        errorField.setText(message);
    }

    private void hideErrorMessage(){
        errorField.setVisible(false);
    }
}
