package springAngularApp.system.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springAngularApp.users.domain.entities.User;
import springAngularApp.users.domain.entities.UserAuthority;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.repositories.UserAuthorityRepository;
import springAngularApp.users.domain.repositories.UserGroupRepository;
import springAngularApp.users.domain.repositories.UserRepository;

import static org.springframework.http.HttpStatus.OK;
import static springAngularApp.users.domain.entities.UserAuthorities.*;

@RestController
public class InitDBController {

    @Autowired private UserAuthorityRepository userAuthorityRepository;
    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserRepository userRepository;

    @Transactional
    @RequestMapping(value = "/initDB")
    public ResponseEntity greeting() {
        userRepository.deleteAll();
        userGroupRepository.deleteAll();
        userAuthorityRepository.deleteAll();
        UserGroup adminGroup = new UserGroup("Administrator");
        UserGroup defaultUserGroup = new UserGroup("Default user");
        UserAuthority roleDefault = new UserAuthority(ROLE_DEFAULT);
        UserAuthority roleUserEdit = new UserAuthority(ROLE_USER_EDIT);
        UserAuthority roleUserView = new UserAuthority(ROLE_USER_VIEW);
        UserAuthority roleUserDelete = new UserAuthority(ROLE_USER_DELETE);
        UserAuthority roleConfigurationView = new UserAuthority(ROLE_CONFIGURATION_VIEW);
        adminGroup.getAuthorities().add(roleDefault);
        adminGroup.getAuthorities().add(roleUserEdit);
        adminGroup.getAuthorities().add(roleUserView);
        adminGroup.getAuthorities().add(roleUserDelete);
        adminGroup.getAuthorities().add(roleConfigurationView);
        defaultUserGroup.getAuthorities().add(roleDefault);
        User admin = new User("admin", "password", adminGroup);
        admin.setSystemUser(true);
        admin.setFirstName("Bob");
        admin.setLastName("Smith");
        User user = new User("user", "password", defaultUserGroup);
        admin.setFirstName("Adam");
        admin.setLastName("Hansen");
        userAuthorityRepository.save(roleDefault);
        userAuthorityRepository.save(roleUserEdit);
        userAuthorityRepository.save(roleUserView);
        userAuthorityRepository.save(roleUserDelete);
        userAuthorityRepository.save(roleConfigurationView);
        userGroupRepository.save(adminGroup);
        userGroupRepository.save(defaultUserGroup);
        userRepository.save(admin);
        userRepository.save(user);
        return new ResponseEntity(OK);
    }

}
