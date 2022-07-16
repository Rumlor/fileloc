package com.fileloc.application.apprepo.appuserrepopackage;

import com.fileloc.application.apprepo.ApplicationBaseRepository;
import com.fileloc.application.domain.appuser.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface AppUserRepository extends ApplicationBaseRepository<AppUser> {
    @EntityGraph(attributePaths = "rolesOfUser",
            type = EntityGraph.EntityGraphType.FETCH)
    Optional<AppUser> findAppUserWithAllAssociatedRolesByUserName(String userName);
}
