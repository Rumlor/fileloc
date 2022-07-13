package com.fileloc.application.appconfig.security.securitybeans;

import com.fileloc.application.apprepo.appuserrepopackage.AppUserRepository;
import com.fileloc.application.domain.appuser.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.function.Supplier;

@Slf4j
public class AppSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser =null;

        Supplier<UsernameNotFoundException> exceptionSupplier =
                 () -> new UsernameNotFoundException(String.format("Username %s not found",username));

        appUser =  appUserRepository.findAppUserByUserName(username).orElseThrow(exceptionSupplier);

        return new AppSecurityDelegationUser(appUser);
    }



}
