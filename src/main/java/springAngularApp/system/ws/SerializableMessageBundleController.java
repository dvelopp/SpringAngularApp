package springAngularApp.system.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springAngularApp.system.configuration.SerializableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/ws/messageBundle/properties")
public class SerializableMessageBundleController {

    @Autowired private SerializableResourceBundleMessageSource messageBundle;

    @RequestMapping(method = GET)
    public Properties properties(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }

}