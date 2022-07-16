package com.fileloc.application.domain.appuser;

import com.fileloc.application.domain.ApplicationBaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class AppUser extends ApplicationBaseEntity {


    private String userName;

    private CompositePassword userCompositePassword;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "user_to_role_link_table",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    /*Bidirectional association*/
    private Set<AppUserRole> rolesOfUser = new HashSet<>();

    private boolean isAccountExpired;

    private boolean isAccountLocked;

}
