package springAngularApp.users.ws;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.users.domain.repositories.UserRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static springAngularApp.users.domain.entities.User.OLD_PASSWORD_MASK;
import static springAngularApp.users.ws.UserValidationConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

    @InjectMocks private UserValidator testee;
    @Mock private UserRepository userRepository;
    @Mock private Errors errors;

    @Test
    public void validate_NameIsNotEmpty_NoErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setUserName("Test User Name");

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(USER_NAME_FIELD, USER_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_NameIsEmpty_ErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();

        testee.validate(userCommand, errors);

        verify(errors, times(1)).rejectValue(USER_NAME_FIELD, USER_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_FirstNameIsNotEmpty_NoErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setFirstName("Test First Name");

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(USER_FIRST_NAME_FIELD, FIRST_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_FirstNameIsEmpty_ErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();

        testee.validate(userCommand, errors);

        verify(errors, times(1)).rejectValue(USER_FIRST_NAME_FIELD, FIRST_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_LastNameIsNotEmpty_NoErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setLastName("Test Last Name");

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(USER_LAST_NAME_FIELD, LAST_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_LastNameIsEmpty_ErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();

        testee.validate(userCommand, errors);

        verify(errors, times(1)).rejectValue(USER_LAST_NAME_FIELD, LAST_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_UserGroupIsNotEmpty_NoErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setUserGroupId("Test User Group Id");

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(USER_GROUP_FIELD, USER_GROUP_IS_EMPTY_CODE);
    }

    @Test
    public void validate_UserGroupIsEmpty_ErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();

        testee.validate(userCommand, errors);

        verify(errors, times(1)).rejectValue(USER_GROUP_FIELD, USER_GROUP_IS_EMPTY_CODE);
    }

    @Test
    public void validate_PasswordIsNotEmpty_NoErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setPassword("Test Password");

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(PASSWORD_FIELD, PASSWORD_IS_EMPTY_CODE);
    }

    @Test
    public void validate_PasswordIsEmpty_ErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();

        testee.validate(userCommand, errors);

        verify(errors, times(1)).rejectValue(PASSWORD_FIELD, PASSWORD_IS_EMPTY_CODE);
    }

    @Test
    public void validate_PasswordIsTooShort_ErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();
        String fiveCharactersLengthPassword = RandomStringUtils.random(5);
        userCommand.setPassword(fiveCharactersLengthPassword);

        testee.validate(userCommand, errors);

        verify(errors).rejectValue(PASSWORD_FIELD, PASSWORD_IS_WEAK_CODE);
    }

    @Test
    public void validate_PasswordSizeIsMinimum_ErrorsHaveNotBeenAdded(){
        UserCommand userCommand = new UserCommand();
        String sixCharactersLengthPassword = RandomStringUtils.random(6);
        userCommand.setPassword(sixCharactersLengthPassword);

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(PASSWORD_FIELD, PASSWORD_IS_WEAK_CODE);
    }

    @Test
    public void validate_PasswordSizeIsBiggerThanMinimum_ErrorsHaveNotBeenAdded(){
        UserCommand userCommand = new UserCommand();
        String sevenCharactersLengthPassword = RandomStringUtils.random(7);
        userCommand.setPassword(sevenCharactersLengthPassword);

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(PASSWORD_FIELD, PASSWORD_IS_WEAK_CODE);
    }

    @Test
    public void validate_PasswordSizeIsTooSmallButThePasswordEqualsOldPasswordMask_ErrorsHaveNotBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setPassword(OLD_PASSWORD_MASK);

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(PASSWORD_FIELD, PASSWORD_IS_WEAK_CODE);
    }

    @Test
    public void validate_UserWithSuchNameAlreadyExists_ErrorsHaveBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setUserName("Test name");
        when(userRepository.existsByName(userCommand.getUserName())).thenReturn(true);

        testee.validate(userCommand, errors);

        verify(errors, times(1)).rejectValue(USER_NAME_FIELD, USER_NAME_EXISTS_CODE);
    }

    @Test
    public void validate_UserWithSuchNameDoesNotExist_ErrorsHaveNotBeenAdded(){
        UserCommand userCommand = new UserCommand();
        userCommand.setUserName("Test name");
        when(userRepository.existsByName(userCommand.getUserName())).thenReturn(false);

        testee.validate(userCommand, errors);

        verify(errors, times(0)).rejectValue(USER_NAME_FIELD, USER_NAME_EXISTS_CODE);
    }

}