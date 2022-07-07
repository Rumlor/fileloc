package com.fileloc.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;

/**Application UI Helper class to tweak visual aspects of components.**/
public class UIComponentGenericStyler  {

public static <T extends HasSize> void setComponentSizeFull(T component){
    component.setSizeFull();
}


}
