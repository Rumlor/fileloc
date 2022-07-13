package com.fileloc.application.domain.appuser;

import com.fileloc.application.applicationconstants.encrpytions.AppUserPasswordEncryptionAlgorithm;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
public class CompositePassword {
    private String userEncryptedPassword;

    @Enumerated(EnumType.STRING)
    private AppUserPasswordEncryptionAlgorithm encryptionAlgorithm;
}
