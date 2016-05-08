package springAngularApp.navigation.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import springAngularApp.navigation.ws.schema.NavigationModelResponse;
import springAngularApp.system.service.AuthProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static springAngularApp.system.utils.JsonUtils.fromJSON;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_CONFIGURATION_VIEW;

@RunWith(MockitoJUnitRunner.class)
public class NavigationWebServiceTest {

    @InjectMocks private NavigationWebService testee;
    @Mock private AuthProvider authProvider;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void attributes_HasConfigurationViewAccess_TrueHasBeenReturned() throws Exception {
        when(authProvider.hasRole(ROLE_CONFIGURATION_VIEW)).thenReturn(true);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        NavigationModelResponse actualNavigationModelResponse = getNavigationResponse(response);
        assertThat(actualNavigationModelResponse.isHasConfigurationViewAccess()).isTrue();
    }

    @Test
    public void attributes_HasNoConfigurationViewAccess_FalseHasBeenReturned() throws Exception {
        when(authProvider.hasRole(ROLE_CONFIGURATION_VIEW)).thenReturn(false);

        MockHttpServletResponse response = sendValidRequestForAttributes();

        NavigationModelResponse actualNavigationModelResponse = getNavigationResponse(response);
        assertThat(actualNavigationModelResponse.isHasConfigurationViewAccess()).isFalse();
    }

    private NavigationModelResponse getNavigationResponse(MockHttpServletResponse response) throws java.io.IOException {
        return fromJSON(NavigationModelResponse.class, response.getContentAsString());
    }

    private MockHttpServletResponse sendValidRequestForAttributes() throws Exception {
        return mockMvc.perform(get("/ws/navigation/model")
                    .contentType(APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
    }

}