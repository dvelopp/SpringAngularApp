package springAngularApp.users.service.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.users.domain.entities.UserAuthority;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.domain.repositories.UserAuthorityRepository;
import springAngularApp.users.domain.repositories.UserGroupRepository;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static springAngularApp.users.domain.entities.UserAuthorityFixture.createDefaultUserAuthority;
import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup;
import static springAngularApp.users.domain.model.UserGroupCommandFixture.createDefaultUserGroupCommand;
import static springAngularApp.users.domain.model.UserGroupCommandFixture.createUserGroupCommandWithEmptyId;
import static springAngularApp.users.service.UserAuthorityService.USER_AUTHORITY_TO_LINK_FUNCTION;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupCommandMapperTest {

    @InjectMocks private UserGroupCommandMapper testee;
    @Mock private UserAuthorityRepository userAuthorityRepository;
    @Mock private UserGroupRepository userGroupRepository;
    
    @Test
    public void mapToCommand_EntityHasBeenMappedToTheCommand(){
        UserGroup userGroup = createDefaultUserGroup();
        userGroup.getAuthorities().add(createDefaultUserAuthority());
        userGroup.getAuthorities().add(createDefaultUserAuthority());

        UserGroupCommand command = testee.mapToCommand(userGroup);

        assertMapToCommand(userGroup, command);
    }

    private void assertMapToCommand(UserGroup userGroup, UserGroupCommand command) {
        assertThat(command.getId()).isEqualTo(userGroup.getId());
        assertThat(command.getName()).isEqualTo(userGroup.getName());
        assertThat(command.isSuperUserGroup()).isEqualTo(userGroup.isSuperUserGroup());
        for (int i = 0; i < command.getAuthorities().size(); i++) {
            IdNameCommand userAuthorityCommand = command.getAuthorities().get(i);
            UserAuthority userAuthority = userGroup.getAuthorities().get(i);
            assertThat(userAuthorityCommand.getId()).isEqualTo(userAuthority.getId());
            assertThat(userAuthorityCommand.getName()).isEqualTo(userAuthority.getName());
        }
    }
/*
    @Test
    public void mapFromCommand_ExistingUserGroup_CommandHasBeenMappedToExistingUserGroup() {
        UserGroupCommand command = createDefaultUserGroupCommand();
        when(userGroupRepository.findOne(command.getId())).thenReturn(createDefaultUserGroup());
        mockUserAuthoritiesForMapFromCommand(command);

        UserGroup userGroup = testee.mapFromCommand(command);

        assertMapFromCommand(command, userGroup);
    }*/
/*
    @Test
    public void mapFromCommand_NewUser_CommandHasBeenMappedToNewUser() {
        UserGroupCommand command = createUserGroupCommandWithEmptyId();
        mockUserAuthoritiesForMapFromCommand(command);

        UserGroup userGroup = testee.mapFromCommand(command);

        assertMapFromCommand(command, userGroup);
    }*/

/*    private void mockUserAuthoritiesForMapFromCommand(UserGroupCommand command) {
        UserAuthority userAuthority = createDefaultUserAuthority();
        IdNameCommand userAuthorityLink = USER_AUTHORITY_TO_LINK_FUNCTION.apply(userAuthority);
        command.getAuthorities().add(userAuthorityLink);
        when(userAuthorityRepository.findAll(asList(userAuthority.getId()))).thenReturn(asList(userAuthority));
    }*/

    private void assertMapFromCommand(UserGroupCommand command, UserGroup userGroup) {
        assertThat(userGroup.isSuperUserGroup()).isEqualTo(command.isSuperUserGroup());
        assertThat(userGroup.getName()).isEqualTo(command.getName());
        for (int i = 0; i < userGroup.getAuthorities().size(); i++) {
            IdNameCommand currentUserAuthorityCommand = command.getAuthorities().get(i);
            UserAuthority currentUserAuthority = userGroup.getAuthorities().get(i);
            assertThat(currentUserAuthorityCommand.getId()).isEqualTo(currentUserAuthority.getId());
        }
    }

}