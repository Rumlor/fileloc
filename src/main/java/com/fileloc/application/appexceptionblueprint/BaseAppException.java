package com.fileloc.application.appexceptionblueprint;

import org.jsoup.internal.StringUtil;

public abstract class BaseAppException extends RuntimeException {


    public BaseAppException(String exceptionCause , String hint) {
            super(exceptionCause+"\n"+hint);
    }


}
