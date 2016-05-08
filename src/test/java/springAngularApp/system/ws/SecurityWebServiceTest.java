package springAngularApp.system.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class SecurityWebServiceTest {

    @InjectMocks private SecurityWebService testee;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void user_InjectedPrincipalReturned() throws Exception {
        TestPrincipal injectedPrincipal = new TestPrincipal("TestPrincipal");

        Principal actualPrincipal = testee.user(injectedPrincipal);

        assertThat(actualPrincipal).isEqualTo(injectedPrincipal);
    }

    private static class TestPrincipal implements Principal {

        private String name;

        public TestPrincipal(String name) {
            this.name = name;
        }

        public TestPrincipal() {
        }

        @Override
        public String getName() {
            return name;
        }

    }

}