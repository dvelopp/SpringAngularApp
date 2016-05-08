package springAngularApp.users.ws;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.system.service.AuthProvider;
import springAngularApp.system.utils.JsonUtils;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.model.UserCommand;
import springAngularApp.users.service.UserGroupService;
import springAngularApp.users.service.UserService;
import springAngularApp.users.ws.schema.UserConfigurationModelResponse;
import springAngularApp.users.ws.validation.UserValidator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static springAngularApp.system.utils.JsonUtils.fromJSON;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_DELETE;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_EDIT;
import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup;
import static springAngularApp.users.domain.model.UserCommandFixture.createDefaultUserCommand;

@RunWith(MockitoJUnitRunner.class)
public class UserWebServiceTest {

    @InjectMocks private UserWebService testee;
    @Mock private UserGroupService userGroupService;
    @Mock private UserValidator userValidator;
    @Mock private AuthProvider authProvider;
    @Mock private UserService userService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        when(userValidator.supports(any())).thenReturn(true);
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void model_HasDeleteUserAccess_DeleteAccessIsTrueInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_DELETE)).thenReturn(true);

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.isHasUserDeleteAccess()).isTrue();
    }

    @Test
    public void model_HasNoDeleteUserAccess_DeleteAccessIsFalseInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_DELETE)).thenReturn(false);

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.isHasUserDeleteAccess()).isFalse();
    }

    @Test
    public void model_HasEditUserAccess_EditAccessIsTrueInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_EDIT)).thenReturn(true);

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.isHasUserEditAccess()).isTrue();
    }

    @Test
    public void model_HasNoEditUserAccess_EditAccessIsFalseInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_EDIT)).thenReturn(false);

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.isHasUserEditAccess()).isFalse();
    }

    @Test
    public void model_HasUserGroups_UserGroupsHaveBeenAdded() throws Exception {
        IdNameCommand command = new IdNameCommand(createDefaultUserGroup(), UserGroup::getId, UserGroup::getName);
        when(userGroupService.getUserGroupLinks()).thenReturn(asList(command));

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.getUserGroups()).containsOnly(command);
    }

    @Test
    public void model_HasNoUserGroups_UserGroupsHaveNotBeenAdded() throws Exception {
        when(userGroupService.getUserGroupLinks()).thenReturn(emptyList());

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.getUserGroups()).isEmpty();
    }

    @Test
    public void model_HasUsers_UsersHaveBeenAdded() throws Exception {
        UserCommand command = createDefaultUserCommand();
        when(userService.getUsers()).thenReturn(asList(command));

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.getUsers()).containsOnly(command);
    }

    @Test
    public void model_HasNoUsers_UsersHaveNotBeenAdded() throws Exception {
        when(userService.getUsers()).thenReturn(emptyList());

        MockHttpServletResponse response = requestModel();

        UserConfigurationModelResponse actualUserConfigurationModelResponse = getUserListResponse(response);
        assertThat(actualUserConfigurationModelResponse.getUsers()).isEmpty();
    }

    @Test
    public void delete_UserHasBeenDeleted() throws Exception {
        UserCommand user = createDefaultUserCommand();

        mockMvc.perform(delete("/ws/users/{userId}", user.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        verify(userService).delete(user.getId());
    }

    @Test
    public void save_UserHasBeenSaved() throws Exception {
        UserCommand user = createDefaultUserCommand();
        String userJson = JsonUtils.toJSON(user);
        mockMvc.perform(post("/ws/users")
                .content(userJson)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        verify(userService).save(user);
    }

    private UserConfigurationModelResponse getUserListResponse(MockHttpServletResponse response) throws java.io.IOException {
        return fromJSON(UserConfigurationModelResponse.class, response.getContentAsString());
    }

    private MockHttpServletResponse requestModel() throws Exception {
        return mockMvc.perform(get("/ws/users/model")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

}