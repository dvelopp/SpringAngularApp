package springAngularApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springAngularApp.system.configuration.MvcConfiguration;
import springAngularApp.system.configuration.SpringJpaConfig;
import springAngularApp.system.configuration.WebSecurityConfig;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(transactionManagerRef = "txManager", entityManagerFactoryRef = "entityManagerFactory")
@Import({SpringJpaConfig.class, WebSecurityConfig.class, MvcConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}