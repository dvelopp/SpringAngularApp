package springAngularApp.users.ws.validation

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.validation.Errors
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll
import springAngularApp.users.domain.model.UserCommand
import springAngularApp.users.domain.repositories.UserRepository

import static springAngularApp.users.domain.entities.User.OLD_PASSWORD_MASK
import static springAngularApp.users.ws.validation.UserValidationConstants.*

@Title("User validation specification")
@Narrative("""
As a user I want to be able to create a new user in the system. The data is entered by hand and there could be mistakes
in that data. Before processing further the data should be validated to find those particular mistakes and show
them to the user and provide hints to fix them.
""")
class UserValidatorSpec extends Specification {

    UserRepository userRepository = Mock();
    Errors errors = Mock();

    UserValidator testee = new UserValidator(
            userRepository: userRepository,
    )

    def setup() {
        userRepository.existsByName(_) >> false;
    }

    def "No errors should be added when user name is not empty"() {
        given: "valid user name"
        UserCommand userCommand = new UserCommand(userName: "Test User Name");
        when: "validate"
        testee.validate(userCommand, errors);
        then: "no errors should be added"
        0 * errors.rejectValue(USER_NAME_FIELD, USER_NAME_IS_EMPTY_CODE);
    }

    def "Errors must be added when user name is empty"() {
        given: "empty user name"
        UserCommand userCommand = new UserCommand();
        when: "validate"
        testee.validate(userCommand, errors);
        then: "errors should be added"
        1 * errors.rejectValue(USER_NAME_FIELD, USER_NAME_IS_EMPTY_CODE);
    }

    def "No errors should be added when first name is not empty"() {
        given: "valid first name"
        UserCommand userCommand = new UserCommand(firstName: "Test First Name");
        when: "validate"
        testee.validate(userCommand, errors);
        then: "no errors should be added"
        0 * errors.rejectValue(USER_FIRST_NAME_FIELD, FIRST_NAME_IS_EMPTY_CODE);
    }

    def "Errors must be added when first name is empty"() {
        given: "empty first name"
        UserCommand userCommand = new UserCommand();
        when: "validate"
        testee.validate(userCommand, errors);
        then: "errors should be added"
        1 * errors.rejectValue(USER_FIRST_NAME_FIELD, FIRST_NAME_IS_EMPTY_CODE);
    }

    def "No errors should be added when last name is not empty"() {
        given: "valid last name"
        UserCommand userCommand = new UserCommand(lastName: "Test Last Name");
        when: "validate"
        testee.validate(userCommand, errors);
        then: "no errors should be added"
        0 * errors.rejectValue(USER_LAST_NAME_FIELD, LAST_NAME_IS_EMPTY_CODE);
    }

    def "Errors must be added when last name is empty"() {
        given: "empty last name"
        UserCommand userCommand = new UserCommand();
        when: "validate"
        testee.validate(userCommand, errors);
        then: "errors should be added"
        1 * errors.rejectValue(USER_LAST_NAME_FIELD, LAST_NAME_IS_EMPTY_CODE);
    }

    def "No errors should be added when user group is selected"() {
        given: "user group is selected"
        UserCommand userCommand = new UserCommand(userGroupId: "Test User Group Id");
        when: "validate"
        testee.validate(userCommand, errors);
        then: "no errors should be added"
        0 * errors.rejectValue(USER_GROUP_FIELD, USER_GROUP_IS_EMPTY_CODE);
    }

    def "Errors must be added when user group is not selected"() {
        given: "user group is not selected"
        UserCommand userCommand = new UserCommand();
        when: "validate"
        testee.validate(userCommand, errors);
        then: "errors should be added"
        1 * errors.rejectValue(USER_GROUP_FIELD, USER_GROUP_IS_EMPTY_CODE);
    }

    def "No errors should be added when password is not empty"() {
        given: "valid password"
        UserCommand userCommand = new UserCommand(password: "Test Password");
        when: "validate"
        testee.validate(userCommand, errors);
        then: "no errors should be added"
        0 * errors.rejectValue(PASSWORD_FIELD, PASSWORD_IS_EMPTY_CODE);
    }

    def "Errors must be added when password is empty"() {
        given: "empty password"
        UserCommand userCommand = new UserCommand();
        when: "validate"
        testee.validate(userCommand, errors);
        then: "errors must be added"
        1 * errors.rejectValue(PASSWORD_FIELD, PASSWORD_IS_EMPTY_CODE);
    }

    @Unroll
    def "When the password length is #passwordLength then errors exist is #errorsShouldBeAdded"() {
        given: "password with length #passwordLength"
        String sevenCharactersLengthPassword = RandomStringUtils.random(passwordLength);
        UserCommand userCommand = new UserCommand(password: sevenCharactersLengthPassword);
        def expectedErrorsNumber = errorsShouldBeAdded ? 1 : 0;
        when: "validate"
        testee.validate(userCommand, errors);
        then: "errors must be added is #errorsShouldBeAdded"
        expectedErrorsNumber * errors.rejectValue(PASSWORD_FIELD, PASSWORD_IS_WEAK_CODE);
        where: "passwordLength=#passwordLength; errorsShouldBeAdded=#errorsShouldBeAdded;"
        passwordLength | errorsShouldBeAdded
        1              | true
        5              | true
        6              | false
        7              | false
        1000           | false
        10000          | false
    }

    def "Errors should not be added if password equals to the old password even though it is too short"() {
        given: "password that equals to old password"
        UserCommand userCommand = new UserCommand(password: OLD_PASSWORD_MASK);
        when: "validate"
        testee.validate(userCommand, errors);
        then: "errors mustn't be added"
        0 * errors.rejectValue(PASSWORD_FIELD, PASSWORD_IS_WEAK_CODE);
    }

    def "Errors must be added when the user with the same name already exists"() {
        given: "user with the name that equals to existing user name"
        UserCommand userCommand = new UserCommand(userName: "Test name");
        when: "validate"
        userRepository.existsByName(userCommand.getUserName()) >> true;
        testee.validate(userCommand, errors);
        then: "errors must be added"
        1 * errors.rejectValue(USER_NAME_FIELD, USER_NAME_EXISTS_CODE);
    }

    def "Errors must no be added when the user with the specified name does not exist"() {
        given: "new name in terms of the system"
        UserCommand userCommand = new UserCommand(userName: "Test name");
        when: "validate"
        userRepository.existsByName(userCommand.getUserName()) >> false;
        testee.validate(userCommand, errors);
        then: "errors mustn't be added"
        0 * errors.rejectValue(USER_NAME_FIELD, USER_NAME_EXISTS_CODE);
    }

    @Unroll()
    def "When user exists is #userExists there should be #errorsRejectedNumber errors added to the Binding result"() {
        given: "existing user name is #userExists"
        UserCommand userCommand = new UserCommand(userName: "Test name");
        when: "validate"
        userRepository.existsByName(userCommand.getUserName()) >> userExists;
        testee.validate(userCommand, errors);
        then: "expect #errorsRejectedNumber errors"
        errorsRejectedNumber * errors.rejectValue(USER_NAME_FIELD, USER_NAME_EXISTS_CODE);
        where: "userExists = #userExists; errorsRejectedNumber = #errorsRejectedNumber;"
        userExists | errorsRejectedNumber
        true       | 1
        false      | 0
    }
}
