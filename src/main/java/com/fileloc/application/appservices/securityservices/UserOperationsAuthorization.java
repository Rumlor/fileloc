package com.fileloc.application.appservices.securityservices;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserOperationsAuthorization {
    boolean canUserDelete(Collection<? extends GrantedAuthority> roles);
    boolean canUserDownload(Collection<? extends GrantedAuthority> roles);
    boolean canUserUpload(Collection<? extends GrantedAuthority> roles);
}
