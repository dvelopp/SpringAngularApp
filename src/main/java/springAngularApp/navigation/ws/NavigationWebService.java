package springAngularApp.navigation.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springAngularApp.system.service.AuthProvider;
import springAngularApp.navigation.ws.schema.NavigationResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_CONFIGURATION_VIEW;

@RestController
@RequestMapping("ws/navigation/attributes")
public class NavigationWebService {

    @Autowired private AuthProvider authProvider;

    @RequestMapping(method = GET)
    public NavigationResponse attributes() {
        NavigationResponse response = new NavigationResponse();
        response.setHasConfigurationViewAccess(authProvider.hasRole(ROLE_CONFIGURATION_VIEW));
        return response;
    }

}
