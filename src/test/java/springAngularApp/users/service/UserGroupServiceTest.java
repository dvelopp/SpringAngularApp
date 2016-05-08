package springAngularApp.users.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.entities.UserGroupFixture;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.domain.repositories.UserGroupRepository;
import springAngularApp.users.service.mapper.UserGroupCommandMapper;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup;
import static springAngularApp.users.domain.model.UserGroupCommandFixture.createDefaultUserGroupCommand;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupServiceTest {

    @InjectMocks private UserGroupService testee;
    @Mock private UserGroupRepository userGroupRepository;
    @Mock private UserGroupCommandMapper userGroupCommandMapper;

    @Test
    public void getUserGroupLinks_NoUserGroups_EmptyListHasBeenReturned() {
        when(userGroupRepository.findByOrderByNameAsc()).thenReturn(emptyList());

        List<IdNameCommand> actualUserGroups = testee.getUserGroupLinks();

        assertThat(actualUserGroups).isEmpty();
    }

    @Test
    public void getUserGroupLinks_TwoUserGroups_UserGroupsHaveBeenMappedToCommandsAndReturned() {
        UserGroup firstUserGroup = createDefaultUserGroup();
        UserGroup secondUserGroup = createDefaultUserGroup();
        IdNameCommand firstIdNameCommand = new IdNameCommand(firstUserGroup, UserGroup::getId, UserGroup::getName);
        IdNameCommand secondIdNameCommand = new IdNameCommand(secondUserGroup, UserGroup::getId, UserGroup::getName);
        when(userGroupRepository.findByOrderByNameAsc()).thenReturn(Arrays.asList(firstUserGroup, secondUserGroup));

        List<IdNameCommand> actualUserGroups = testee.getUserGroupLinks();

        assertThat(actualUserGroups).containsExactly(firstIdNameCommand, secondIdNameCommand);
    }

    @Test
    public void getUserGroups_NoUserGroups_EmptyListHasBeenReturned() {
        when(userGroupRepository.findByOrderByNameAsc()).thenReturn(emptyList());

        List<UserGroupCommand> actualUserGroups = testee.getUserGroups();

        assertThat(actualUserGroups).isEmpty();
    }

    @Test
    public void getUserGroups_TwoUserGroups_UserGroupsHaveBeenMappedToCommandsAndReturned() {
        UserGroup firstUserGroup = createDefaultUserGroup();
        UserGroup secondUserGroup = createDefaultUserGroup();
        UserGroupCommand firstUserGroupCommand = createDefaultUserGroupCommand();
        UserGroupCommand secondUserGroupCommand = createDefaultUserGroupCommand();
        when(userGroupRepository.findByOrderByNameAsc()).thenReturn(Arrays.asList(firstUserGroup, secondUserGroup));
        when(userGroupCommandMapper.mapToCommand(firstUserGroup)).thenReturn(firstUserGroupCommand);
        when(userGroupCommandMapper.mapToCommand(secondUserGroup)).thenReturn(secondUserGroupCommand);

        List<UserGroupCommand> actualUserGroups = testee.getUserGroups();

        assertThat(actualUserGroups).containsExactly(firstUserGroupCommand, secondUserGroupCommand);
    }

    @Test
    public void save_ValidUserGroupCommand_UserGroupHasBeenSaved(){
        UserGroupCommand userGroupCommand = createDefaultUserGroupCommand();
        UserGroup userGroup = createDefaultUserGroup();
        when(userGroupCommandMapper.mapFromCommand(userGroupCommand)).thenReturn(userGroup);

        testee.save(userGroupCommand);

        verify(userGroupRepository).save(userGroup);
    }

    @Test
    public void delete_GeneralGroup_UserGroupHasBeenDeleted() {
        UserGroup userGroup = createDefaultUserGroup();
        when(userGroupRepository.findOne(userGroup.getId())).thenReturn(userGroup);

        testee.delete(userGroup.getId());

        verify(userGroupRepository).delete(userGroup.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_SuperUserGroup_ExceptionHasBeenThrown(){
        UserGroup superUserGroup = UserGroupFixture.builder().setSuperUserGroup(true).build();
        when(userGroupRepository.findOne(superUserGroup.getId())).thenReturn(superUserGroup);

        testee.delete(superUserGroup.getId());
    }

}