package springAngularApp.users.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.domain.repositories.UserAuthorityRepository;
import springAngularApp.users.domain.repositories.UserGroupRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static springAngularApp.users.service.UserAuthorityService.USER_AUTHORITY_TO_LINK_FUNCTION;

@Component
public class UserGroupCommandMapper {

    @Autowired private UserAuthorityRepository userAuthorityRepository;
    @Autowired private UserGroupRepository userGroupRepository;

    public UserGroupCommand mapToCommand(UserGroup userGroup) {
        UserGroupCommand command = new UserGroupCommand();
        command.setName(userGroup.getName());
        command.setId(userGroup.getId());
        List<IdNameCommand> authorities = userGroup.getAuthorities().stream()
                .map(USER_AUTHORITY_TO_LINK_FUNCTION)
                .collect(toList());
        command.setAuthorities(authorities);
        command.setSuperUserGroup(userGroup.isSuperUserGroup());
        return command;
    }

    public UserGroup mapFromCommand(UserGroupCommand command) {
        UserGroup userGroup;
        if (isNotBlank(command.getId())) {
            userGroup = userGroupRepository.findById(command.getId()).get();
        } else {
            userGroup = new UserGroup(command.getName());
        }
        List<String> authoritiesIds = command.getAuthorities().stream()
                .map(IdNameCommand::getId)
                .collect(toList());
        userGroup.setAuthorities(userAuthorityRepository.findAllById(authoritiesIds));
        userGroup.setName(command.getName());
        return userGroup;
    }

}
