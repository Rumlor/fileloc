package com.fileloc.application.appservices.securityservices;

import com.fileloc.application.domain.appobjects.UserRegistrationObject;
import com.fileloc.application.domain.appuser.AppUser;
import com.fileloc.application.views.applogin.RegistrationPage;


public interface AppRegistrationService {

    AppUser registerUserWithSpecifiedCredentials(UserRegistrationObject object);

}
