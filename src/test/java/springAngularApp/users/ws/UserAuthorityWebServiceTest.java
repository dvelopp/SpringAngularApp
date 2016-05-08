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
import springAngularApp.system.ws.schema.LinkListResponse;
import springAngularApp.users.domain.entities.UserGroup;
import springAngularApp.users.service.UserAuthorityService;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static springAngularApp.system.utils.JsonUtils.fromJSON;
import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthorityWebServiceTest {

    @InjectMocks private UserAuthorityWebService testee;
    @Mock private UserAuthorityService userAuthorityService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void links_HasUserAuthorities_LinksToAuthoritiesHaveBeenAdded() throws Exception {
        IdNameCommand command = new IdNameCommand(createDefaultUserGroup(), UserGroup::getId, UserGroup::getName);
        when(userAuthorityService.getUserAuthoritiesLinks()).thenReturn(asList(command));

        MockHttpServletResponse response = requestLinks();

        LinkListResponse actualUserGroupLinkListResponse = getLinkListResponse(response);
        assertThat(actualUserGroupLinkListResponse.getLinks()).containsOnly(command);
    }

    @Test
    public void links_HasNoUserAuthorities_NoLinksHaveBeenAdded() throws Exception {
        when(userAuthorityService.getUserAuthoritiesLinks()).thenReturn(Collections.<IdNameCommand>emptyList());

        MockHttpServletResponse response = requestLinks();

        LinkListResponse actualUserGroupLinkListResponse = getLinkListResponse(response);
        assertThat(actualUserGroupLinkListResponse.getLinks()).isEmpty();
    }

    private MockHttpServletResponse requestLinks() throws Exception {
        return mockMvc.perform(get("/ws/users/authorities/links")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    private LinkListResponse getLinkListResponse(MockHttpServletResponse response) throws java.io.IOException {
        return fromJSON(LinkListResponse.class, response.getContentAsString());
    }

}