package springAngularApp.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.users.domain.repositories.UserGroupRepository;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@Service
public class UserGroupService {

    @Autowired private UserGroupRepository userGroupRepository;

    @Transactional(readOnly = true)
    public List<IdNameCommand> getUserGroups() {
        return newArrayList(userGroupRepository.findAll()).stream()
                .map(userGroup -> new IdNameCommand(userGroup, UserGroup::getId, UserGroup::getName))
                .collect(toList());
    }

}
