package springAngularApp.users.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import springAngularApp.users.domain.entities.User;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.users.domain.repositories.UserGroupRepository;
import springAngularApp.users.domain.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static springAngularApp.users.domain.entities.User.OLD_PASSWORD_MASK;
import static springAngularApp.users.domain.entities.UserFixture.createDefaultUser;
import static springAngularApp.users.domain.model.UserCommandFixture.createDefaultUserCommand;
import static springAngularApp.users.domain.model.UserCommandFixture.createUserCommandWithEmptyId;

@RunWith(MockitoJUnitRunner.class)
public class UserCommandMapperTest {

    @InjectMocks private UserCommandMapper testee;
    @Mock private UserGroupRepository userGroupRepository;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArguments()[0]);
    }

    @Test
    public void mapToCommand_ValidUser_CommandHasBeenCreatedBasedOnUser() {
        User user = createDefaultUser();

        UserCommand userCommand = testee.mapToCommand(user);

        assertMapToCommand(user, userCommand);
    }

    private void assertMapToCommand(User user, UserCommand userCommand) {
        assertThat(userCommand.getId()).isEqualTo(user.getId());
        assertThat(userCommand.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userCommand.getLastName()).isEqualTo(user.getLastName());
        assertThat(userCommand.getUserName()).isEqualTo(user.getName());
        assertThat(userCommand.getPassword()).isEqualTo(user.getPassword());
        assertThat(userCommand.getUserGroupId()).isEqualTo(user.getGroup().getId());
        assertThat(userCommand.getUserGroupName()).isEqualTo(user.getGroup().getName());
        assertThat(userCommand.getSystemUser()).isEqualTo(user.isSystemUser());
    }

    @Test
    public void mapFromCommand_ExistingUser_CommandHasBeenMappedToExistingUser() {
        User user = createDefaultUser();
        UserCommand userCommand = createDefaultUserCommand();
        when(userRepository.findOne(userCommand.getId())).thenReturn(user);
        when(userGroupRepository.findOne(userCommand.getUserGroupId())).thenReturn(user.getGroup());

        testee.mapFromCommand(userCommand);

        assertMapFromCommand(user);
    }

    @Test
    public void mapFromCommand_NewUser_CommandHasBeenMappedToNewUser() {
        User user = createDefaultUser();
        UserCommand userCommand = createUserCommandWithEmptyId();
        when(userGroupRepository.findOne(userCommand.getUserGroupId())).thenReturn(user.getGroup());

        testee.mapFromCommand(userCommand);

        assertMapFromCommand(user);
    }

    private void assertMapFromCommand(User user) {
        assertThat(user.getId()).isEqualTo(user.getId());
        assertThat(user.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(user.getLastName()).isEqualTo(user.getLastName());
        assertThat(user.getName()).isEqualTo(user.getName());
        assertThat(user.getPassword()).isEqualTo(user.getPassword());
        assertThat(user.getGroup().getId()).isEqualTo(user.getGroup().getId());
    }

    @Test
    public void mapFromCommand_PasswordHasNotBeenChanged_PasswordHasNotBeenUpdated() {
        User user = createDefaultUser();
        UserCommand userCommand = createDefaultUserCommand();
        when(userRepository.findOne(userCommand.getId())).thenReturn(user);
        when(userGroupRepository.findOne(userCommand.getUserGroupId())).thenReturn(user.getGroup());
        userCommand.setPassword(OLD_PASSWORD_MASK);

        testee.mapFromCommand(userCommand);

        verifyZeroInteractions(passwordEncoder);
    }

    @Test
    public void mapFromCommand_PasswordHasBeenChanged_PasswordHasBeenUpdated() {
        String newPassword = "New Password";
        User user = createDefaultUser();
        UserCommand userCommand = createDefaultUserCommand();
        when(userRepository.findOne(userCommand.getId())).thenReturn(user);
        when(userGroupRepository.findOne(userCommand.getUserGroupId())).thenReturn(user.getGroup());
        userCommand.setPassword(newPassword);

        testee.mapFromCommand(userCommand);

        verify(passwordEncoder).encode(newPassword);
    }

}