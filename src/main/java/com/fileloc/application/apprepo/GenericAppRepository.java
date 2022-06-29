package com.fileloc.application.apprepo;

import com.fileloc.application.domain.ApplicationBaseEntity;

public interface GenericAppRepository<T extends ApplicationBaseEntity>
        extends ApplicationBaseRepository<T>{

}
