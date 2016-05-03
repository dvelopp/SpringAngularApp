package springAngularApp.home.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import springAngularApp.system.service.AuthProvider;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class HomeWebServiceTest {

    @InjectMocks private HomeWebService testee;
    @Mock private AuthProvider authProvider;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void attributes_StatusIsOk() throws Exception {
        mockMvc.perform(get("/ws/home/attributes")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}