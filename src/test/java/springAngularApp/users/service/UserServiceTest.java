package springAngularApp.users.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import springAngularApp.users.domain.entities.User;
import springAngularApp.users.domain.entities.UserFixture;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.users.domain.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static springAngularApp.users.domain.entities.UserFixture.createDefaultUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks private UserService testee;

    @Mock private UserCommandMapper userCommandMapper;
    @Mock private UserRepository userRepository;

    @Test
    public void save_ValidUserCommand_UserHasBeenSaved(){
        UserCommand userCommand = new UserCommand();
        User user = createDefaultUser();
        when(userCommandMapper.mapFromCommand(userCommand)).thenReturn(user);

        testee.save(userCommand);

        verify(userRepository).save(user);
    }

    @Test
    public void delete_GeneralUser_UserHasBeenDeleted() {
        User user = createDefaultUser();
        when(userRepository.findOne(user.getId())).thenReturn(user);

        testee.delete(user.getId());

        verify(userRepository).delete(user.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_SystemUser_ExceptionHasBeenThrown(){
        User user = UserFixture.builder().setSystemUser(true).build();
        when(userRepository.findOne(user.getId())).thenReturn(user);

        testee.delete(user.getId());
    }

    @Test
    public void getUsers_NoUsers_EmptyListHaveBeenReturned(){
        when(userRepository.findByOrderByFirstNameAsc()).thenReturn(emptyList());

        List<UserCommand> actualUsers = testee.getUsers();

        assertThat(actualUsers).isEmpty();
    }

    @Test
    public void getUsers_TwoUsers_UsersHaveBeenMappedToCommandsAndReturned(){
        User firstUser = UserFixture.createDefaultUser();
        User secondUser = UserFixture.createDefaultUser();
        UserCommand firstUserCommand = new UserCommand();
        UserCommand secondUserCommand = new UserCommand();
        when(userRepository.findByOrderByFirstNameAsc()).thenReturn(Arrays.asList(firstUser, secondUser));
        when(userCommandMapper.mapToCommand(firstUser)).thenReturn(firstUserCommand);
        when(userCommandMapper.mapToCommand(secondUser)).thenReturn(secondUserCommand);

        List<UserCommand> actualUsers = testee.getUsers();

        assertThat(actualUsers).containsExactly(firstUserCommand, secondUserCommand);
    }

}