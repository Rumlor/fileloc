package com.fileloc.application.appconfig.security.securitybeans;

import com.fileloc.application.applicationconstants.encrpytions.AppUserPasswordEncryptionAlgorithm;
import com.vaadin.flow.component.UI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppSecurityAuthenticationProviderService implements AuthenticationProvider {


    @Autowired
    private UserDetailsService appSecurityUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("User to be authenticated is {} with raw password {}",authentication.getName(),String.valueOf(authentication.getCredentials()));
        //explicit down-casting is legal here.
       AppSecurityDelegationUser userToBeAuthenticated = (AppSecurityDelegationUser) appSecurityUserDetailsService.loadUserByUsername(authentication.getName());
       var pass = userToBeAuthenticated.getPassword();
       var algorithm = userToBeAuthenticated.getAppUser().getUserCompositePassword().getEncryptionAlgorithm();
       boolean result = algorithm.equals(AppUserPasswordEncryptionAlgorithm.BCRYPT) ?
               matchEncryptionForBcrypt(String.valueOf(authentication.getCredentials()),pass) :
               matchEncryptionForScrypt(String.valueOf(authentication.getCredentials()),pass);

       if (result) {
           log.info("Successfully authenticated..");
           return new UsernamePasswordAuthenticationToken(
                   userToBeAuthenticated.getUsername(),
                   userToBeAuthenticated.getPassword(),
                   userToBeAuthenticated.getAuthorities());

       }else
       {
          throw new BadCredentialsException("User password is incorrect");
       }
    }

    /* Defaults to true */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private boolean matchEncryptionForBcrypt(String credentials,String pass){
        return bCryptPasswordEncoder.matches(credentials,pass);
    }

    private boolean matchEncryptionForScrypt(String credentials,String pass){
        return sCryptPasswordEncoder.matches(credentials,pass);
    }


}
