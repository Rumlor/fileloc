package com.fileloc.application.appconfig.security;

import com.fileloc.application.appconfig.security.securitybeans.AppSecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class AppSecurityBeanContainer {

    @Bean
    public UserDetailsService appUserDetailsService(){return new AppSecurityUserDetailsService();}
    /**Users have two different encoding algorithms for  their password. used algorithm will be stored in its own entity
     * column **/
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder() {
        return new SCryptPasswordEncoder();
    }

}
