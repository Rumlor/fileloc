package com.fileloc.application.appservices.securityservices;

import com.fileloc.application.applicationconstants.appcontentconstants.AppRouting;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAppServiceImpl implements SecurityContextAppService{


    @Override
    public UserDetails getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        var authenticatedUser = context.getAuthentication();
        var principal = authenticatedUser.getPrincipal();
        if(principal instanceof UserDetails)
            return (UserDetails) principal;

        return null;
    }

    @Override
    public void logout() {
        UI.getCurrent().getPage().setLocation(AppRouting.LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent(),null,null);
    }
}
