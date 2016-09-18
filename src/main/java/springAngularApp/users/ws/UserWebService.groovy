package springAngularApp.users.ws

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import springAngularApp.system.service.AuthProvider
import springAngularApp.users.domain.model.UserCommand
import springAngularApp.users.service.UserGroupService
import springAngularApp.users.service.UserService
import springAngularApp.users.ws.schema.UserConfigurationModelResponse
import springAngularApp.users.ws.validation.UserValidator

import javax.validation.Valid

import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.*
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_DELETE
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_EDIT

@RestController
@RequestMapping(value = "ws/users")
class UserWebService {

    @Autowired UserGroupService userGroupService;
    @Autowired UserValidator userValidator;
    @Autowired AuthProvider authProvider;
    @Autowired UserService userService;

    @InitBinder
    initBinder(WebDataBinder binder) {
        binder.setValidator userValidator;
    }

    @RequestMapping(value = "/{userId}", method = DELETE)
    def delete(@PathVariable String userId) {
        userService.delete userId
        new ResponseEntity<String>(OK)
    }

    @RequestMapping(method = POST)
    def save(@Valid @RequestBody UserCommand userCommand) {
        userService.save userCommand
        new ResponseEntity<String>(OK)
    }

    @RequestMapping(method = GET, value = "/model")
    def getModel() {
        new UserConfigurationModelResponse(
                hasUserDeleteAccess: authProvider.hasRole(ROLE_USER_DELETE),
                hasUserEditAccess: authProvider.hasRole(ROLE_USER_EDIT),
                userGroups: userGroupService.getUserGroupLinks(),
                users: userService.getUsers(),
        )
    }

}
