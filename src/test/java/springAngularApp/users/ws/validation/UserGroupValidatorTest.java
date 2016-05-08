package springAngularApp.users.ws.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.domain.repositories.UserGroupRepository;

import static org.mockito.Mockito.*;
import static springAngularApp.users.ws.validation.UserGroupValidationConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupValidatorTest {

    @InjectMocks private UserGroupValidator testee;
    @Mock private UserGroupRepository userGroupRepository;
    @Mock private Errors errors;

    @Test
    public void validate_NameIsNotEmpty_NoErrorsHaveBeenAdded(){
        UserGroupCommand userGroupCommand = new UserGroupCommand();
        userGroupCommand.setName("Test User Group Name");

        testee.validate(userGroupCommand, errors);

        verify(errors, times(0)).rejectValue(USER_GROUP_NAME_FIELD, USER_GROUP_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_NameIsEmpty_ErrorsHaveBeenAdded(){
        UserGroupCommand userGroupCommand = new UserGroupCommand();

        testee.validate(userGroupCommand, errors);

        verify(errors, times(1)).rejectValue(USER_GROUP_NAME_FIELD, USER_GROUP_NAME_IS_EMPTY_CODE);
    }

    @Test
    public void validate_UserGroupWithSuchNameAlreadyExists_ErrorsHaveBeenAdded(){
        UserGroupCommand userGroupCommand = new UserGroupCommand();
        userGroupCommand.setName("Test name");
        when(userGroupRepository.existsByName(userGroupCommand.getName())).thenReturn(true);

        testee.validate(userGroupCommand, errors);

        verify(errors, times(1)).rejectValue(USER_GROUP_NAME_FIELD, USER_GROUP_NAME_EXISTS_CODE);
    }

    @Test
    public void validate_UserGroupWithSuchNameDoesNotExist_ErrorsHaveNotBeenAdded(){
        UserGroupCommand userGroupCommand = new UserGroupCommand();
        userGroupCommand.setName("Test name");
        when(userGroupRepository.existsByName(userGroupCommand.getName())).thenReturn(false);

        testee.validate(userGroupCommand, errors);

        verify(errors, times(0)).rejectValue(USER_GROUP_NAME_FIELD, USER_GROUP_NAME_EXISTS_CODE);
    }

}