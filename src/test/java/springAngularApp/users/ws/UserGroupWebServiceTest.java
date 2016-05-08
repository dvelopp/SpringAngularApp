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
import springAngularApp.system.ws.schema.LinkListResponse;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.domain.model.UserGroupCommand;
import springAngularApp.users.service.UserGroupService;
import springAngularApp.users.ws.schema.UserGroupConfigurationModelResponse;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static springAngularApp.system.utils.JsonUtils.fromJSON;
import static springAngularApp.system.utils.JsonUtils.toJSON;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_DELETE;
import static springAngularApp.users.domain.entities.UserAuthorities.ROLE_USER_GROUP_EDIT;
import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup;
import static springAngularApp.users.domain.model.UserGroupCommandFixture.createDefaultUserGroupCommand;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupWebServiceTest {

    @InjectMocks private UserGroupWebService testee;
    @Mock private UserGroupService userGroupService;
    @Mock private AuthProvider authProvider;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void links_HasGroups_GroupsHaveBeenAdded() throws Exception {
        IdNameCommand command = new IdNameCommand(createDefaultUserGroup(), UserGroup::getId, UserGroup::getName);
        when(userGroupService.getUserGroupLinks()).thenReturn(asList(command));

        MockHttpServletResponse response = requestLinks();

        LinkListResponse actualUserGroupLinkListResponse = getUserGroupsResponse(response);
        assertThat(actualUserGroupLinkListResponse.getLinks()).containsOnly(command);
    }

    @Test
    public void links_HasNoGroups_NoGroupsHaveBeenAdded() throws Exception {
        when(userGroupService.getUserGroupLinks()).thenReturn(Collections.<IdNameCommand>emptyList());

        MockHttpServletResponse response = requestLinks();

        LinkListResponse actualUserGroupLinkListResponse = getUserGroupsResponse(response);
        assertThat(actualUserGroupLinkListResponse.getLinks()).isEmpty();
    }

    @Test
    public void model_HasEditUserGroupAccess_EditAccessIsTrueInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_GROUP_EDIT)).thenReturn(true);

        MockHttpServletResponse response = requestModel();

        UserGroupConfigurationModelResponse actualResponse =
                fromJSON(UserGroupConfigurationModelResponse.class, response.getContentAsString());
        assertThat(actualResponse.isHasUserGroupEditAccess()).isTrue();
    }

    @Test
    public void model_HasNoEditUserGroupAccess_EditAccessIsFalseInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_GROUP_EDIT)).thenReturn(false);

        MockHttpServletResponse response = requestModel();

        UserGroupConfigurationModelResponse actualResponse =
                fromJSON(UserGroupConfigurationModelResponse.class, response.getContentAsString());
        assertThat(actualResponse.isHasUserGroupEditAccess()).isFalse();
    }

    @Test
    public void model_HasDeleteUserGroupAccess_DeleteAccessIsTrueInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_GROUP_DELETE)).thenReturn(true);

        MockHttpServletResponse response = requestModel();

        UserGroupConfigurationModelResponse actualResponse =
                fromJSON(UserGroupConfigurationModelResponse.class, response.getContentAsString());
        assertThat(actualResponse.isHasUserGroupDeleteAccess()).isTrue();
    }

    @Test
    public void model_HasNoDeleteUserGroupAccess_DeleteAccessIsFalseInResponse() throws Exception {
        when(authProvider.hasRole(ROLE_USER_GROUP_DELETE)).thenReturn(false);

        MockHttpServletResponse response = requestModel();

        UserGroupConfigurationModelResponse actualResponse =
                fromJSON(UserGroupConfigurationModelResponse.class, response.getContentAsString());
        assertThat(actualResponse.isHasUserGroupDeleteAccess()).isFalse();
    }

    @Test
    public void delete_UserGroupHasBeenDeleted() throws Exception {
        UserGroupCommand userGroupCommand = createDefaultUserGroupCommand();

        mockMvc.perform(delete("/ws/users/groups/{userGroupId}", userGroupCommand.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        verify(userGroupService).delete(userGroupCommand.getId());
    }

    @Test
    public void save_UserGroupHasBeenSaved() throws Exception {
        UserGroupCommand userGroupCommand = createDefaultUserGroupCommand();
        String userJson = toJSON(userGroupCommand);
        mockMvc.perform(post("/ws/users/groups")
                .content(userJson)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        verify(userGroupService).save(userGroupCommand);
    }

    private LinkListResponse getUserGroupsResponse(MockHttpServletResponse response) throws java.io.IOException {
        return fromJSON(LinkListResponse.class, response.getContentAsString());
    }

    private MockHttpServletResponse requestLinks() throws Exception {
        return mockMvc.perform(get("/ws/users/groups/links")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    private MockHttpServletResponse requestModel() throws Exception {
        return mockMvc.perform(get("/ws/users/groups/model")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

}