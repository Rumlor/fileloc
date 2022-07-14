package com.fileloc.application.domain.appobjects;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class SecurityContextUserCredential
{
    private String userName;
    private String userPass;
    private Collection<? extends GrantedAuthority> roles;

    public SecurityContextUserCredential(Authentication authentication) {
        this.userName  = authentication.getName();
        this.roles = authentication.getAuthorities();
        this.userPass = String.valueOf(authentication.getCredentials());
    }
}
