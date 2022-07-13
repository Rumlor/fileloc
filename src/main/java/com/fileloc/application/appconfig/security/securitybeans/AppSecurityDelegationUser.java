package com.fileloc.application.appconfig.security.securitybeans;

import com.fileloc.application.domain.appuser.AppUser;
import com.fileloc.application.domain.appuser.AppUserRole;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter

public class AppSecurityDelegationUser implements UserDetails {
    private final AppUser appUser;
    public AppSecurityDelegationUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUser.getRolesOfUser().stream().map(AppUserRole::getRoleOfAppUser).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return appUser.getUserCompositePassword().getUserEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !appUser.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !appUser.isAccountLocked();
    }
    /**This attribute of app user is redundant for current needs relating authorization **/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**This attribute of app user is redundant for current needs regarding authorization **/
    @Override
    public boolean isEnabled() {
        return true;
    }
}
