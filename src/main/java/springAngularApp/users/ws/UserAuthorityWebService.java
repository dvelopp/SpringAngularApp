package springAngularApp.users.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springAngularApp.users.service.UserAuthorityService;
import springAngularApp.system.ws.schema.LinkListResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "ws/users/authorities")
public class UserAuthorityWebService {

    @Autowired private UserAuthorityService userAuthorityService;

    @RequestMapping(method = GET, value = "/links")
    public LinkListResponse links() {
        return new LinkListResponse(userAuthorityService.getUserAuthoritiesLinks());
    }

}
