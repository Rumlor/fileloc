package com.fileloc.application.applicationconstants.AppUserRoles;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRoleConstant implements GrantedAuthority {
    SUPERUSER ("this role has access to every privilege app has for users and encapsulates other two child roles","Super User"),
    DOWNLOADER("this role has access to only data retrieval privileges in app","Downloader User"),
    UPLOADER("this role has access to only data insertion privileges in app","Uploader User")
    ;

    private String roleDefinition;
    private String role;
    AppUserRoleConstant(String roleDefinition, String role) {
        this.roleDefinition = roleDefinition;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    public String getRoleDefinition() {
        return this.roleDefinition;
    }

    public String getRole(){
        return this.role;
    }

    public static AppUserRoleConstant[] getAllConstants(){
        return new AppUserRoleConstant[]{AppUserRoleConstant.SUPERUSER,AppUserRoleConstant.DOWNLOADER,AppUserRoleConstant.UPLOADER};
    }
}
