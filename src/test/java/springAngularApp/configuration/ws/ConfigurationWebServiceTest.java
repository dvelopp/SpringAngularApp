package springAngularApp.configuration.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import springAngularApp.configuration.ws.schema.ConfigurationResponse;
import springAngularApp.system.service.AuthProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_VIEW;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationWebServiceTest {

    @InjectMocks private ConfigurationWebService testee;
    @Mock private AuthProvider authProvider;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void attributes_HasConfigurationViewAccess_ConfigurationViewAccessIsTrueInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_VIEW)).thenReturn(true);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        ConfigurationResponse actualConfigurationResponse = getConfigurationResponse(response);
        assertThat(actualConfigurationResponse.isHasUserViewAccess()).isTrue();
    }

    @Test
    public void attributes_HasNoConfigurationViewAccess_ConfigurationViewAccessIsFalseInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_VIEW)).thenReturn(false);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        ConfigurationResponse actualConfigurationResponse = getConfigurationResponse(response);
        assertThat(actualConfigurationResponse.isHasUserViewAccess()).isFalse();
    }

    private ConfigurationResponse getConfigurationResponse(MockHttpServletResponse response) throws java.io.IOException {
        return objectMapper.readValue(response.getContentAsString(), ConfigurationResponse.class);
    }

    private MockHttpServletResponse sendValidRequestForAttributes() throws Exception {
        return mockMvc.perform(get("/ws/configuration/attributes")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }


}