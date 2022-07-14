package com.fileloc.application.appservices.securityservices;

import com.fileloc.application.applicationconstants.AppUserRoles.AppUserRoleConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserOperationsAuthorizationService implements UserOperationsAuthorization{

    @Override
    public boolean canUserDelete(Collection<? extends GrantedAuthority> roles) {
        return roles.contains(AppUserRoleConstant.SUPERUSER);
    }

    @Override
    public boolean canUserDownload(Collection<? extends GrantedAuthority> roles) {
        return roles.stream().anyMatch(role->
                role.equals(AppUserRoleConstant.DOWNLOADER)
                ||
                role.equals(AppUserRoleConstant.SUPERUSER));

    }

    @Override
    public boolean canUserUpload(Collection<? extends GrantedAuthority> roles) {
        return roles.stream().anyMatch(role->
                role.equals(AppUserRoleConstant.UPLOADER)
                        ||
                        role.equals(AppUserRoleConstant.SUPERUSER));
    }
}
