package springAngularApp.navigation.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springAngularApp.system.service.AuthProvider;
import springAngularApp.navigation.ws.schema.NavigationModelResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_CONFIGURATION_VIEW;

@RestController
@RequestMapping("ws/navigation")
public class NavigationWebService {

    @Autowired private AuthProvider authProvider;

    @RequestMapping(method = GET, value = "/model")
    public NavigationModelResponse model() {
        NavigationModelResponse response = new NavigationModelResponse();
        response.setHasConfigurationViewAccess(authProvider.hasRole(ROLE_CONFIGURATION_VIEW));
        return response;
    }

}
