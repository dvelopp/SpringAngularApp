package springAngularApp.users.ws.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.domain.repositories.UserGroupRepository;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static springAngularApp.users.ws.validation.UserGroupValidationConstants.USER_GROUP_NAME_EXISTS_CODE;
import static springAngularApp.users.ws.validation.UserGroupValidationConstants.USER_GROUP_NAME_FIELD;
import static springAngularApp.users.ws.validation.UserGroupValidationConstants.USER_GROUP_NAME_IS_EMPTY_CODE;

@Component
public class UserGroupValidator implements Validator {

    @Autowired private UserGroupRepository userGroupRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserGroupCommand.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserGroupCommand command = (UserGroupCommand) target;
        validateName(errors, command);
    }

    private void validateName(Errors errors, UserGroupCommand command) {
        if (isBlank(command.getName())) {
            errors.rejectValue(USER_GROUP_NAME_FIELD, USER_GROUP_NAME_IS_EMPTY_CODE);
        } else {
            if(command.isNew()){
                if( userGroupRepository.existsByName(command.getName())){
                    errors.rejectValue(USER_GROUP_NAME_FIELD, USER_GROUP_NAME_EXISTS_CODE);
                }
            }
        }
    }

}
