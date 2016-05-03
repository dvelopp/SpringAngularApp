package springAngularApp.system.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springAngularApp.users.domain.entities.*;
import springAngularApp.users.domain.repositories.UserRepository;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static springAngularApp.users.domain.entities.UserAuthorityFixture.createDefaultUserAuthority;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks private CustomUserDetailsService testee;
    @Mock private UserRepository userRepository;

    @Test
    public void loadUserByUsername_UserExists_UserDetailsHaveBeenCreatedForSelectedUser() {
        UserAuthority userAuthority = createDefaultUserAuthority();
        UserGroup userGroup = UserGroupFixture.builder().setAuthorities(asList(userAuthority)).build();
        User user = UserFixture.builder().setGroup(userGroup).build();
        when(userRepository.findByName(user.getName())).thenReturn(user);

        UserDetails userDetails = testee.loadUserByUsername(user.getName());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertThat(authorities.stream().anyMatch(it -> it.getAuthority().equals(userAuthority.getName()))).isTrue();
        assertThat(userDetails.getUsername()).isEqualTo(user.getName());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void loadUserByUsername_UserDoesNotExist_NullHasBeenReturned() {
        UserDetails userDetails = testee.loadUserByUsername("Invalid name");

        assertThat(userDetails).isNull();
    }

}