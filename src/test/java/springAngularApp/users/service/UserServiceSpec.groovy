package springAngularApp.users.service

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import springAngularApp.users.domain.entities.User
import springAngularApp.users.domain.entities.UserFixture
import springAngularApp.users.domain.model.UserCommand
import springAngularApp.users.domain.repositories.UserRepository
import springAngularApp.users.service.mapper.UserCommandMapper

import static springAngularApp.users.domain.entities.UserFixture.createDefaultUser

@Title("User service")
@Narrative("""
As a used I want to be able to view and manage users. So that I need a layer between the domain and
the UI to provide this functionality.
""")
class UserServiceSpec extends Specification {

    UserRepository userRepository = Mock();
    UserCommandMapper userCommandMapper = Mock();

    UserService testee = new UserService(
            userRepository: userRepository,
            userCommandMapper: userCommandMapper
    );

    def "When service saves a valid user, it should be saved"() {
        given: "valid user"
        def userCommand = new UserCommand();
        def user = createDefaultUser();
        userCommandMapper.mapFromCommand(userCommand) >> user;
        when: "save"
        testee.save userCommand;
        then: "user will be mapped and saved"
        1 * userRepository.save(user);
    }

    def "When service deletes an existing user, it should delegate the call to the repository"() {
        given: "regular user"
        def user = createDefaultUser();
        userRepository.findOne(user.getId()) >> user;
        when: "delete"
        testee.delete user.getId();
        then: "repository should delete the selected user"
        1 * userRepository.delete(user.getId());
    }

    def "When service deletes an existing system user, exception is thrown"() {
        given: "system user"
        def user = UserFixture.builder().setSystemUser(true).build();
        userRepository.findOne(user.getId()) >> user;
        when: "delete"
        testee.delete user.getId();
        then: "exception should be thrown"
        thrown IllegalArgumentException
    }

    def "When service tries to get users and doesn't find them it has to return an empty list"() {
        given: "users don't exist"
        userRepository.findByOrderByFirstNameAsc() >> [];
        when: "get users"
        List<UserCommand> actualUsers = testee.getUsers();
        then: "empty list will be returned"
        actualUsers.isEmpty()
    }

    def "When service tries to get users and there are two users, two users will be returned"(){
        given: "two user exist"
        User firstUser = createDefaultUser();
        User secondUser = createDefaultUser();
        UserCommand firstUserCommand = new UserCommand();
        UserCommand secondUserCommand = new UserCommand();
        userRepository.findByOrderByFirstNameAsc() >> [firstUser, secondUser];
        userCommandMapper.mapToCommand(firstUser) >> firstUserCommand;
        userCommandMapper.mapToCommand(secondUser) >> secondUserCommand;
        when: "get users"
        List<UserCommand> actualUsers = testee.getUsers();
        then: "two users will be returned"
        actualUsers.size() == 2
        actualUsers.containsAll([firstUserCommand, secondUserCommand])
    }
}
