package springAngularApp.users.domain.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springAngularApp.system.domain.HibernateIntegrationTest;
import springAngularApp.users.domain.entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import static springAngularApp.users.domain.entities.UserFixture.createUserWithFirstName;
import static springAngularApp.users.domain.entities.UserFixture.createUserWithName;

public class UserRepositoryTest extends HibernateIntegrationTest<User> {

    @Autowired private UserRepository userRepository;

    @Test
    public void findByOrderByFirstNameAsc_TwoUsers_TwoUsersHaveBeenReturned(){
        User firstUser = createUserWithFirstName("A");
        User secondUser = createUserWithFirstName("B");
        saveAll(firstUser, secondUser).flush();

        Iterable<User> actualUsers = userRepository.findAll();

        assertThat(actualUsers).hasSize(2);
    }

    @Test
    public void findByOrderByFirstNameAsc_TwoUsersSavedInWrongOrder_UsersHaveBeenReturnedInTheCorrectOrder(){
        User firstUser = createUserWithFirstName("B");
        User secondUser = createUserWithFirstName("A");
        saveAll(firstUser, secondUser).flush();

        Iterable<User> actualUsers = userRepository.findByOrderByFirstNameAsc();

        assertThat(actualUsers).containsExactly(secondUser, firstUser);
    }

    @Test
    public void findByOrderByFirstNameAsc_TwoUsersSavedInCorrectOrder_UsersHaveBeenReturnedInTheCorrectOrder(){
        User firstUser = createUserWithFirstName("A");
        User secondUser = createUserWithFirstName("B");
        saveAll(firstUser, secondUser).flush();

        Iterable<User> actualUsers = userRepository.findByOrderByFirstNameAsc();

        assertThat(actualUsers).containsExactly(firstUser, secondUser);
    }

    @Test
    public void existsByName_UserWithSelectedNameExists_TrueHasBeenReturned(){
        User firstUser = createUserWithName("Test User Name");
        saveAll(firstUser).flush();

        Boolean userExists = userRepository.existsByName(firstUser.getName());

        assertThat(userExists).isTrue();
    }

    @Test
    public void existsByName_UserWithSelectedNameDoesNotExist_FalseHasBeenReturned(){
        User firstUser = createUserWithName("Test User Name");
        saveAll(firstUser).flush();

        Boolean userExists = userRepository.existsByName("Invalid user name");

        assertThat(userExists).isFalse();
    }

    @Test
    public void existsByName_NoUsersExist_FalseHasBeenReturned(){
        Boolean userExists = userRepository.existsByName("Invalid user name");

        assertThat(userExists).isFalse();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
