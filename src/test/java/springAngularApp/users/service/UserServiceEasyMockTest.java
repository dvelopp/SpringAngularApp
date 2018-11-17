package springAngularApp.users.service;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import springAngularApp.users.domain.entities.User;
import springAngularApp.users.domain.entities.UserFixture;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.users.domain.repositories.UserRepository;
import springAngularApp.users.service.mapper.UserCommandMapper;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;
import static springAngularApp.users.domain.entities.UserFixture.createDefaultUser;

@RunWith(EasyMockRunner.class)
public class UserServiceEasyMockTest {

    @TestSubject private UserService testee = new UserService();

    @Mock private UserCommandMapper userCommandMapper;
    @Mock private UserRepository userRepository;

    @Test
    public void save_ValidUserCommand_UserHasBeenSaved() {
        UserCommand userCommand = new UserCommand();
        User user = createDefaultUser();
        expect(userRepository.save(user)).andReturn(user);
        expect(userCommandMapper.mapFromCommand(userCommand)).andReturn(user);
        replay(userRepository, userCommandMapper);

        testee.save(userCommand);

        verify(userRepository, userCommandMapper);
    }

 /*   @Test
    public void delete_GeneralUser_UserHasBeenDeleted() {
        User user = createDefaultUser();
        expect(userRepository.findOne(user.getId())).andReturn(user);
        userRepository.delete(user.getId());
        replay(userRepository);

        testee.delete(user.getId());

        verify(userRepository);
    }*/
/*
    @Test(expected = IllegalArgumentException.class)
    public void delete_SystemUser_ExceptionHasBeenThrown() {
        User user = UserFixture.builder().setSystemUser(true).build();
        expect(userRepository.findOne(user.getId())).andReturn(user);
        replay(userRepository);

        testee.delete(user.getId());
    }*/

    @Test
    public void getUsers_NoUsers_EmptyListHaveBeenReturned(){
        expect(userRepository.findByOrderByFirstNameAsc()).andReturn(emptyList());
        replay(userRepository);

        List<UserCommand> actualUsers = testee.getUsers();

        assertThat(actualUsers).isEmpty();
    }

    @Test
    public void getUsers_TwoUsers_UsersHaveBeenMappedToCommandsAndReturned(){
        User firstUser = UserFixture.createDefaultUser();
        User secondUser = UserFixture.createDefaultUser();
        UserCommand firstUserCommand = new UserCommand();
        UserCommand secondUserCommand = new UserCommand();
        expect(userRepository.findByOrderByFirstNameAsc()).andReturn(Arrays.asList(firstUser, secondUser));
        expect(userCommandMapper.mapToCommand(firstUser)).andReturn(firstUserCommand);
        expect(userCommandMapper.mapToCommand(secondUser)).andReturn(secondUserCommand);
        replay(userRepository, userCommandMapper);

        List<UserCommand> actualUsers = testee.getUsers();

        assertThat(actualUsers).containsExactly(firstUserCommand, secondUserCommand);
        verify(userRepository, userCommandMapper);
    }

}