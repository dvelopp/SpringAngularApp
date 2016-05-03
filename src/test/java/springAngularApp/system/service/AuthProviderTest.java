package springAngularApp.system.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import java.util.Arrays;
import java.util.Collection;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class AuthProviderTest {

    private static final String TEST_ROLE_NAME = "ROLE_TEST";

    @InjectMocks private AuthProvider testee;
    @Mock private SecurityContext context;
    @Mock private Authentication authentication;

    @Before
    public void setup(){
        testee = new AuthProvider(){
            @Override
            protected SecurityContext getContext() {
                return context;
            }
        };
        initMocks(this);
        when(context.getAuthentication()).thenReturn(authentication);
    }
    
    @Test
    public void hasRole_RoleExists_UserHasSelectedRole(){
        GrantedAuthority authority = new SimpleGrantedAuthority(TEST_ROLE_NAME);
        Collection<? extends GrantedAuthority> grantedAuthorities = Arrays.asList(authority);
        when(authentication.getAuthorities()).thenAnswer(invocation -> grantedAuthorities);

        boolean hasRole = testee.hasRole(TEST_ROLE_NAME);

        assertThat(hasRole).isTrue();
    }

    @Test
    public void hasRole_RoleDoesNotExist_UserHasNotSelectedRole(){
        when(authentication.getAuthorities()).thenAnswer(invocation -> emptyList());

        boolean hasRole = testee.hasRole(TEST_ROLE_NAME);

        assertThat(hasRole).isFalse();
    }

}