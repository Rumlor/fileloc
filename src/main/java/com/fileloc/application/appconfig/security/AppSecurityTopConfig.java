package com.fileloc.application.appconfig.security;

import com.fileloc.application.appconfig.security.securitybeans.AppSecurityAuthenticationProviderService;
import com.fileloc.application.views.applogin.LoginPage;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/** By extending vaadin framework security configurer adapter
 * helper class, we enabled view based authentication filter**/
@Configuration
@EnableWebSecurity
public class AppSecurityTopConfig
        extends VaadinWebSecurityConfigurerAdapter {

    @Autowired
    private AppSecurityAuthenticationProviderService authProvider;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**");
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginPage.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}
