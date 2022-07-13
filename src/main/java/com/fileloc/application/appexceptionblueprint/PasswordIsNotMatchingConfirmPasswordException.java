package com.fileloc.application.appexceptionblueprint;

public class PasswordIsNotMatchingConfirmPasswordException extends BaseAppException{



    public PasswordIsNotMatchingConfirmPasswordException() {
        super("Your Passwords are not matching.", "Your confirmation password must be same with your user password.");
    }
}
