package com.fileloc.application.domain.appobjects;

import com.fileloc.application.applicationconstants.AppUserRoles.AppUserRoleConstant;
import lombok.Data;

@Data
public class UserRegistrationObject {
    private String userName;
    private String password;
    private String confirmPassword;
    private AppUserRoleConstant userRole;
}
