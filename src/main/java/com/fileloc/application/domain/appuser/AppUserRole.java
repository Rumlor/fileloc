package com.fileloc.application.domain.appuser;

import com.fileloc.application.applicationconstants.AppUserRoles.AppUserRoleConstant;
import com.fileloc.application.domain.ApplicationBaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class AppUserRole extends ApplicationBaseEntity {

    @Enumerated(value = EnumType.ORDINAL)
    private AppUserRoleConstant roleOfAppUser;

    @ManyToMany(mappedBy = "rolesOfUser")
    private Set<AppUser> usersOfRole = new HashSet<>();
}
