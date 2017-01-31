package springAngularApp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springAngularApp.system.configuration.MvcConfiguration;
import springAngularApp.system.configuration.SpringJpaConfig;
import springAngularApp.system.configuration.WebSecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@EnableJpaRepositories
@ComponentScan
@Import({SpringJpaConfig.class, WebSecurityConfig.class, MvcConfiguration.class})
public class AppConfig {
}
