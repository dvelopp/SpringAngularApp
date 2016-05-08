package springAngularApp.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.domain.repositories.UserGroupRepository;
import springAngularApp.users.service.mapper.UserGroupCommandMapper;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_DELETE;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_EDIT;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_VIEW;

@Service
public class UserGroupService {

    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserGroupCommandMapper userGroupCommandMapper;

    public static final Function<UserGroup, IdNameCommand> USER_GROUP_TO_LINK_FUNCTION =
            userGroup -> new IdNameCommand(userGroup, UserGroup::getId, UserGroup::getName);


    @Transactional(readOnly = true)
    public List<UserGroupCommand> getUserGroups() {
        return newArrayList(userGroupRepository.findByOrderByNameAsc()).stream()
                .map(userGroupCommandMapper::mapToCommand)
                .collect(toList());
    }

    @Secured(ROLE_USER_GROUP_VIEW)
    @Transactional(readOnly = true)
    public List<IdNameCommand> getUserGroupLinks() {
        return newArrayList(userGroupRepository.findByOrderByNameAsc()).stream()
                .map(USER_GROUP_TO_LINK_FUNCTION)
                .collect(toList());
    }

    @Secured(ROLE_USER_GROUP_EDIT)
    @Transactional
    public void save(UserGroupCommand userGroupCommand) {
        UserGroup userGroup = userGroupCommandMapper.mapFromCommand(userGroupCommand);
        userGroupRepository.save(userGroup);
    }

    @Secured(ROLE_USER_GROUP_DELETE)
    @Transactional
    public void delete(String userGroupId) {
        UserGroup userGroup = userGroupRepository.findOne(userGroupId);
        if (userGroup.isSuperUserGroup()) {
            throw new IllegalArgumentException("Super user group can't be deleted");
        }
        userGroupRepository.delete(userGroupId);
    }

}
