package com.fileloc.application.views.uploadsection;

import com.fileloc.application.appservices.contracts.FileOutputHandler;
import com.fileloc.application.views.mainpage.MainWebPage;
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

    /**Reference parent component to refresh list after succesfull file upload**/
    private MainWebPage parentMainWebPage;
    public FileUploadComponent(File uploadedFileDirectory, FileOutputHandler fileOutputHandlerService,MainWebPage mainWebPage) {

        this.fileOutputHandlerService = fileOutputHandlerService;
        this.uploadedFileDirectory = uploadedFileDirectory;
        this.parentMainWebPage = mainWebPage;
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

        fileUploader = new Upload(uiListener.uploadFileEventListener(uploadedFileDirectory));
        fileUploader.setDropAllowed(false);
        fileUploader.setSizeFull();
        fileUploader.setMaxFiles(1);
        //in bytes . equals 512 MBs.
        fileUploader.setMaxFileSize(512*1024*1024);
        //upload listener. extra event listeners will be added.
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
      private Receiver uploadFileEventListener(File uploadFileDirectory){

          return  (MultiFileReceiver) (String fileName, String mimeType) -> {
              try {
                  File file = new File(uploadFileDirectory,fileName);
                  return fileOutputHandlerService.fileOutput(file);
              } catch (Exception e) {
                  throw new RuntimeException(e);
              }
          };

      }
      private ComponentEventListener<SucceededEvent> succeededEventComponentEventListener(){
        return event -> {
            parentMainWebPage.fillFileListWithFiles();
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
