package springAngularApp.users.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.system.service.AuthProvider;
import springAngularApp.users.service.UserGroupService;
import springAngularApp.users.service.UserService;
import springAngularApp.users.ws.schema.UserListResponse;

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

    static final String COMMAND_NAME = "userCommand";

    @InitBinder(COMMAND_NAME)
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @RequestMapping(method = GET)
    public UserListResponse list() {
        UserListResponse response = new UserListResponse();
        response.setHasUserDeleteAccess(authProvider.hasRole(ROLE_USER_DELETE));
        response.setHasUserEditAccess(authProvider.hasRole(ROLE_USER_EDIT));
        response.setUserGroups(userGroupService.getUserGroups());
        response.setUsers(userService.getUsers());
        return response;
    }

    @RequestMapping(value = "/{userId}", method = DELETE)
    public ResponseEntity delete(@PathVariable String userId) {
        userService.delete(userId);
        return new ResponseEntity<String>(OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity save(@Validated @ModelAttribute(COMMAND_NAME) UserCommand userCommand) {
        userService.save(userCommand);
        return new ResponseEntity<String>(OK);
    }

}
