package com.fileloc.application.appexceptionblueprint;

public class UnauthorizedUserAccessException extends BaseAppException{

    public UnauthorizedUserAccessException() {
        super("User does not have required privileges to execute given operation","Refer to user roles");
    }
}
