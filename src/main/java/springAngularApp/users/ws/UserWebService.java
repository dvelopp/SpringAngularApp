package springAngularApp.users.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springAngularApp.system.service.AuthProvider;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.users.service.UserGroupService;
import springAngularApp.users.service.UserService;
import springAngularApp.users.ws.schema.UserConfigurationModelResponse;
import springAngularApp.users.ws.validation.UserValidator;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_DELETE;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_EDIT;

@RestController
@RequestMapping(value = "ws/users")
public class UserWebService {

    @Autowired private UserGroupService userGroupService;
    @Autowired private UserValidator userValidator;
    @Autowired private AuthProvider authProvider;
    @Autowired private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @RequestMapping(value = "/{userId}", method = DELETE)
    public ResponseEntity delete(@PathVariable String userId) {
        userService.delete(userId);
        return new ResponseEntity<String>(OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity save(@Valid @RequestBody UserCommand userCommand) {
        userService.save(userCommand);
        return new ResponseEntity<String>(OK);
    }

    @RequestMapping(method = GET, value = "/model")
    public UserConfigurationModelResponse getModel() {
        UserConfigurationModelResponse response = new UserConfigurationModelResponse();
        response.setHasUserDeleteAccess(authProvider.hasRole(ROLE_USER_DELETE));
        response.setHasUserEditAccess(authProvider.hasRole(ROLE_USER_EDIT));
        response.setUserGroups(userGroupService.getUserGroupLinks());
        response.setUsers(userService.getUsers());
        return response;
    }
}
