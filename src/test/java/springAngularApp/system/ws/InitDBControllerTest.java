package springAngularApp.system.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import springAngularApp.users.domain.repositories.UserAuthorityRepository;
import springAngularApp.users.domain.repositories.UserGroupRepository;
import springAngularApp.users.domain.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class InitDBControllerTest {

    @InjectMocks private InitDBController testee;
    @Mock private UserAuthorityRepository userAuthorityRepository;
    @Mock private UserGroupRepository userGroupRepository;
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArguments()[0]);
    }

    @Test
    public void init_StatusIsOK() throws Exception {
        ResponseEntity response = testee.init();

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

}