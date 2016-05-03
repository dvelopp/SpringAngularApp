package springAngularApp.system.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import springAngularApp.system.configuration.SerializableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class SerializableMessageBundleControllerTest {

    @InjectMocks private SerializableMessageBundleController testee;
    @Mock private SerializableResourceBundleMessageSource messageBundle;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void messageBundle_ValidRequest_PropertiesHaveBeenReturned() throws Exception {
        Properties expectedResponse = new Properties();
        expectedResponse.put("TestKey", "TestValue");
        when(messageBundle.getAllProperties(any(Locale.class))).thenReturn(expectedResponse);

        MockHttpServletResponse response = mockMvc.perform(get("/ws/messageBundle/properties")
                .param("lang", "en")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Properties actualMessageBundleResponse = objectMapper.readValue(response.getContentAsString(), Properties.class);
        assertThat(actualMessageBundleResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void messageBundle_NoLanguageParameter_BadRequest() throws Exception {
        mockMvc.perform(get("/ws/messageBundle/properties")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}