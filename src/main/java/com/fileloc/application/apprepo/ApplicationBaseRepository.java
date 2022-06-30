package com.fileloc.application.apprepo;

import com.fileloc.application.domain.ApplicationBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface ApplicationBaseRepository<T extends ApplicationBaseEntity>
        extends JpaRepository<T, Long> {
}
