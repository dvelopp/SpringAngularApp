package springAngularApp.configuration.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import springAngularApp.configuration.ws.schema.ConfigurationModelResponse;
import springAngularApp.system.service.AuthProvider;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static springAngularApp.system.utils.JsonUtils.fromJSON;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_VIEW;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_VIEW;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationWebServiceTest {

    @InjectMocks private ConfigurationWebService testee;
    @Mock private AuthProvider authProvider;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void model_HasUserViewAccess_UserViewAccessIsTrueInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_VIEW)).thenReturn(true);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        ConfigurationModelResponse actualConfigurationModelResponse = getConfigurationResponse(response);
        assertThat(actualConfigurationModelResponse.isHasUserViewAccess()).isTrue();
    }

    @Test
    public void model_HasNoUserViewAccess_UserViewAccessIsFalseInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_VIEW)).thenReturn(false);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        ConfigurationModelResponse actualConfigurationModelResponse = getConfigurationResponse(response);
        assertThat(actualConfigurationModelResponse.isHasUserViewAccess()).isFalse();
    }

    @Test
    public void model_HasUserGroupViewAccess_UserGroupViewAccessIsTrueInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_GROUP_VIEW)).thenReturn(true);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        ConfigurationModelResponse actualConfigurationModelResponse = getConfigurationResponse(response);
        assertThat(actualConfigurationModelResponse.isHasUserGroupViewAccess()).isTrue();
    }

    @Test
    public void model_HasNoUserGroupViewAccess_UserGroupViewAccessIsFalseInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_GROUP_VIEW)).thenReturn(false);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        ConfigurationModelResponse actualConfigurationModelResponse = getConfigurationResponse(response);
        assertThat(actualConfigurationModelResponse.isHasUserGroupViewAccess()).isFalse();
    }

    private ConfigurationModelResponse getConfigurationResponse(MockHttpServletResponse response) throws IOException {
        return fromJSON(ConfigurationModelResponse.class, response.getContentAsString());
    }

    private MockHttpServletResponse sendValidRequestForAttributes() throws Exception {
        return mockMvc.perform(get("/ws/configuration/model")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

}