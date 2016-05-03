package springAngularApp.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springAngularApp.users.domain.entities.User;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.users.domain.repositories.UserGroupRepository;
import springAngularApp.users.domain.repositories.UserRepository;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static springAngularApp.users.domain.entities.User.OLD_PASSWORD_MASK;

@Component
public class UserCommandMapper {

    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public UserCommand mapToCommand(User user) {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(user.getId());
        userCommand.setFirstName(user.getFirstName());
        userCommand.setLastName(user.getLastName());
        userCommand.setUserName(user.getName());
        userCommand.setUserGroupId(user.getGroup().getId());
        userCommand.setUserGroupName(user.getGroup().getName());
        userCommand.setPassword(user.getPassword());
        userCommand.setSystemUser(user.isSystemUser());
        return userCommand;
    }

    public User mapFromCommand(UserCommand userCommand) {
        User user;
        UserGroup userGroup = userGroupRepository.findOne(userCommand.getUserGroupId());
        if (isNotBlank(userCommand.getId())) {
            user = userRepository.findOne(userCommand.getId());
            user.setGroup(userGroup);
        } else {
            user = new User(userCommand.getUserName(), userCommand.getPassword(), userGroup);
        }
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        user.setName(userCommand.getUserName());
        if (!OLD_PASSWORD_MASK.equals(userCommand.getPassword())) {
            user.setPassword(passwordEncoder.encode(userCommand.getPassword()));
        }
        return user;
    }

}
