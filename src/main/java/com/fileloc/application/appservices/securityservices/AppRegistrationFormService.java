package com.fileloc.application.appservices.securityservices;

import com.fileloc.application.appexceptionblueprint.PasswordIsNotMatchingConfirmPasswordException;
import com.fileloc.application.applicationconstants.encrpytions.AppUserPasswordEncryptionAlgorithm;
import com.fileloc.application.apprepo.appuserrepopackage.AppUserRepository;
import com.fileloc.application.domain.appobjects.UserRegistrationObject;
import com.fileloc.application.domain.appuser.AppUser;
import com.fileloc.application.domain.appuser.AppUserRole;
import com.fileloc.application.domain.appuser.CompositePassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class AppRegistrationFormService implements AppRegistrationService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public AppUser registerUserWithSpecifiedCredentials(UserRegistrationObject object) {
        log.info("Save user to db with credentials {}",object);

        if(!object.getPassword().equals(object.getConfirmPassword()))
            throw new PasswordIsNotMatchingConfirmPasswordException();

        AppUser user = new AppUser();
        AppUserRole userRole = new AppUserRole();
        CompositePassword password = new CompositePassword();

        //encryption algorithm is set to default scrypt encoding.
        password.setUserEncryptedPassword(sCryptPasswordEncoder.encode(object.getPassword()));
        password.setEncryptionAlgorithm(AppUserPasswordEncryptionAlgorithm.SCRYPT);

        //set user side of association.
        user.setUserName(object.getUserName());
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.getRolesOfUser().add(userRole);
        user.setUserCompositePassword(password);

        //set role side of association.
        userRole.setRoleOfAppUser(object.getUserRole());
        userRole.getUsersOfRole().add(user);

        var savedUser = appUserRepository.save(user);

        return savedUser;
    }
}
