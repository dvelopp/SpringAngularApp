package springAngularApp.home.ws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("ws/home/attributes")
public class HomeWebService {

    @RequestMapping(method = GET)
    public ResponseEntity attributes() {
        return new ResponseEntity(OK);
    }

}
