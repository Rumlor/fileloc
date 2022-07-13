package com.fileloc.application.appservices.securityservices;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityContextAppService {

    UserDetails getAuthenticatedUser();
    void logout();
}
