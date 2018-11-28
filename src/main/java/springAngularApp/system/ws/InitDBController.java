package springAngularApp.system.ws;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springAngularApp.users.domain.entities.User;
import springAngularApp.users.domain.entities.UserAuthority;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.repositories.UserAuthorityRepository;
import springAngularApp.users.domain.repositories.UserGroupRepository;
import springAngularApp.users.domain.repositories.UserRepository;

import java.io.File;
import java.io.IOException;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.io.Files.readLines;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.http.HttpStatus.OK;
import static springAngularApp.users.domain.entities.UserAuthorities.*;

/**
 * TODO Delete me
 */
@RestController
@SuppressWarnings("all")
public class InitDBController {

    public static final String ADMINISTRATOR = "Administrator";
    public static final String DEFAULT_USER = "Default user";
    @Autowired private UserAuthorityRepository userAuthorityRepository;
    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private SessionFactory sessionFactory;

    @Transactional
    @RequestMapping(value = "/initDB")
    public ResponseEntity init() throws IOException {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM User").executeUpdate();
        sessionFactory.getCurrentSession().createQuery("DELETE FROM UserGroup").executeUpdate();
        sessionFactory.getCurrentSession().createQuery("DELETE FROM UserAuthority").executeUpdate();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        UserGroup adminGroup = new UserGroup(ADMINISTRATOR);
        adminGroup.setSuperUserGroup(true);
        UserGroup defaultUserGroup = new UserGroup(DEFAULT_USER);

        UserAuthority roleDefault = new UserAuthority(ROLE_DEFAULT);
        UserAuthority roleUserEdit = new UserAuthority(ROLE_USER_EDIT);
        UserAuthority roleUserView = new UserAuthority(ROLE_USER_VIEW);
        UserAuthority roleUserDelete = new UserAuthority(ROLE_USER_DELETE);
        UserAuthority roleUserGroupEdit = new UserAuthority(ROLE_USER_GROUP_EDIT);
        UserAuthority roleUserGroupView = new UserAuthority(ROLE_USER_GROUP_VIEW);
        UserAuthority roleUserGroupDelete = new UserAuthority(ROLE_USER_GROUP_DELETE);
        UserAuthority roleConfigurationView = new UserAuthority(ROLE_CONFIGURATION_VIEW);

        adminGroup.getAuthorities().add(roleDefault);
        adminGroup.getAuthorities().add(roleUserEdit);
        adminGroup.getAuthorities().add(roleUserView);
        adminGroup.getAuthorities().add(roleUserDelete);
        adminGroup.getAuthorities().add(roleConfigurationView);
        adminGroup.getAuthorities().add(roleUserGroupEdit);
        adminGroup.getAuthorities().add(roleUserGroupView);
        adminGroup.getAuthorities().add(roleUserGroupDelete);
        defaultUserGroup.getAuthorities().add(roleDefault);

        String password = passwordEncoder.encode("password");

        User admin = new User("admin", password, adminGroup);
        admin.setSystemUser(true);
        admin.setFirstName("Bob");
        admin.setLastName("Smith");

        userAuthorityRepository.saveAll(
                newArrayList(
                        roleDefault,
                        roleUserEdit,
                        roleUserView,
                        roleUserDelete,
                        roleConfigurationView,
                        roleUserGroupEdit,
                        roleUserGroupView,
                        roleUserGroupDelete
                ));
        userGroupRepository.saveAll(newArrayList(adminGroup, defaultUserGroup));
        userRepository.save(admin);
        generateAdditionalUsers(adminGroup, password);
        return new ResponseEntity(OK);
    }

    private void generateAdditionalUsers(UserGroup adminGroup, String password) throws IOException {
        File fileWithNames = new File(getClass().getClassLoader().getResource("utils/names.txt").getFile());
        readLines(fileWithNames, defaultCharset()).forEach(name -> {
            String[] nameParts = name.split(" ");
            User user = new User(nameParts[0] + nameParts[1], password, adminGroup);
            user.setFirstName(nameParts[0]);
            user.setLastName(nameParts[1]);
            userRepository.save(user);
        });
    }

}
