package com.fileloc.application.views.downloadsection;

import com.fileloc.application.appservices.appdataservices.UI_AppServices_Bridge_Impl;
import com.fileloc.application.appservices.contracts.FileInputHandler;
import com.fileloc.application.domain.content.FileEntity;
import com.fileloc.application.views.UIEventHandler;
import com.fileloc.application.views.mainpage.MainWebPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

public class OptionsComponent extends FormLayout {

    private TextField fileName = new TextField("File Name");
    private TextField fileSize = new TextField("File Size");
    private TextField fileLastUpdate = new TextField("File Update Time");

    private TextField fileLocation = new TextField("File Location");

    private Button delete = new Button("Delete");
    private Button download = new Button("Download");

    private Binder<FileEntity> fileEntityBinder = new Binder<>(FileEntity.class);

    private FileEntity file;

    private FileInputHandler handler;

    private MainWebPage mainWebPage;

    private UIEventHandler uiEventHandler;

    public OptionsComponent(FileInputHandler fileInputHandler, MainWebPage mainWebPage,UIEventHandler uiEventHandler) {
        fileEntityBinder.bind(fileName,FileEntity::getFileName,FileEntity::setFileName);
        fileEntityBinder.bind(fileSize,FileEntity::getFileSize,FileEntity::setFileSize);
        fileEntityBinder.bind(fileLastUpdate,getter->getter.getUpdateTime().toString(),(setter,val)->setter.setUpdateTime(LocalDateTime.parse(val)));
        fileEntityBinder.bind(fileLocation,getter->getter.getFileDirectory().getFileLocation().getContainingDirectory(),(setter,val)->setter.getFileDirectory().getFileLocation().setContainingDirectory(val));
        setInstanceFields(fileInputHandler);
        this.mainWebPage = mainWebPage;
        this.uiEventHandler = uiEventHandler;
        this.setVisible(false);
        createButtonsLayout();
        setButtonEvents();
        add(createLayout());
    }

    private void setInstanceFields(FileInputHandler fileInputHandler) {
        this.handler=fileInputHandler;
    }

    private void createButtonsLayout() {
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        download.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private Component[] createLayout() {
        return new Component[]{
                fileName,
                fileSize,
                fileLastUpdate,
                fileLocation,
                new HorizontalLayout(delete,download)};
    }


    public void fillOptions(FileEntity file){
        fileEntityBinder.readBean(file);
        this.file=file;
        StreamResource resource = new StreamResource(this.fileName.getValue(),()->handler.fileInput(file));
        FileDownloadWrapper wrapper = new FileDownloadWrapper(resource);
        wrapper.wrapComponent(download);
        add(wrapper);
    }

    private void setButtonEvents() {

        delete.addClickListener(event->uiEventHandler.deleteEvent(event,this.file,this.mainWebPage));
        //download.addClickListener(clickEvent ->{});
    }


}
