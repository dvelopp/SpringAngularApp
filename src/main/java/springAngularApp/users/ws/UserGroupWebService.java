package springAngularApp.users.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springAngularApp.system.service.AuthProvider;
import springAngularApp.system.ws.schema.LinkListResponse;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.service.UserGroupService;
import springAngularApp.users.ws.schema.UserGroupConfigurationModelResponse;
import springAngularApp.users.ws.validation.UserGroupValidator;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_DELETE;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_EDIT;

@RestController
@RequestMapping(value = "ws/users/groups")
public class UserGroupWebService {

    @Autowired private UserGroupService userGroupService;
    @Autowired private UserGroupValidator userGroupValidator;
    @Autowired private AuthProvider authProvider;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userGroupValidator);
    }

    @RequestMapping(method = GET, value = "/links")
    public LinkListResponse links() {
        return new LinkListResponse(userGroupService.getUserGroupLinks());
    }

    @RequestMapping(method = POST)
    public ResponseEntity save(@Valid @RequestBody UserGroupCommand userGroupCommand) {
        userGroupService.save(userGroupCommand);
        return new ResponseEntity<String>(OK);
    }

    @RequestMapping(value = "/{userGroupId}", method = DELETE)
    public ResponseEntity delete(@PathVariable String userGroupId) {
        userGroupService.delete(userGroupId);
        return new ResponseEntity<String>(OK);
    }

    @RequestMapping(method = GET, value = "/model")
    public UserGroupConfigurationModelResponse model() {
        UserGroupConfigurationModelResponse response = new UserGroupConfigurationModelResponse();
        response.setUserGroups(userGroupService.getUserGroups());
        response.setHasUserGroupDeleteAccess(authProvider.hasRole(ROLE_USER_GROUP_DELETE));
        response.setHasUserGroupEditAccess(authProvider.hasRole(ROLE_USER_GROUP_EDIT));
        return response;
    }

}
