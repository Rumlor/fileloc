package com.fileloc.application.apprepo.appuserrepopackage;

import com.fileloc.application.apprepo.ApplicationBaseRepository;
import com.fileloc.application.domain.appuser.AppUser;

import java.util.Optional;

public interface AppUserRepository extends ApplicationBaseRepository<AppUser> {
    Optional<AppUser> findAppUserByUserName(String userName);
}
