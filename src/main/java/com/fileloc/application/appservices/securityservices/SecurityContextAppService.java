package com.fileloc.application.appservices.securityservices;

import com.fileloc.application.domain.appobjects.SecurityContextUserCredential;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityContextAppService {

    SecurityContextUserCredential getAuthenticatedUser();
    void logout();
}
