package com.fileloc.application.views.mainpage;

import com.fileloc.application.views.uploadsection.FileUploadComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.File;

@PageTitle("list")
@Route(value = "")
public class MainWebPage extends VerticalLayout {

    private FileUploadComponent fileUploadComponent;
    private File file;
    public MainWebPage() {
    file = fileSystemManagerUtility();
    fileUploadComponent = new FileUploadComponent(file);
    add(fileUploadComponent);
    }

    private static File fileSystemManagerUtility(){
        File file = new File("filestor");
        return file.exists() ? file : createDir(file);
    }

    private static File createDir(File file) {
        file.mkdir();
        return file;
    }


}
