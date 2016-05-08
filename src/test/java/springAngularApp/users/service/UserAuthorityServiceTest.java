package springAngularApp.users.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.users.domain.entities.UserAuthority;
import springAngularApp.users.domain.repositories.UserAuthorityRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static springAngularApp.users.domain.entities.UserAuthorityFixture.createDefaultUserAuthority;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthorityServiceTest {

    @InjectMocks private UserAuthorityService testee;
    @Mock private UserAuthorityRepository userAuthorityRepository;

    @Test
    public void getUserAuthorityLinks_NoUserAuthorities_EmptyListHasBeenReturned() {
        when(userAuthorityRepository.findByOrderByNameAsc()).thenReturn(emptyList());

        List<IdNameCommand> actualUserAuthorities = testee.getUserAuthoritiesLinks();

        assertThat(actualUserAuthorities).isEmpty();
    }

    @Test
    public void getUserAuthorityLinks_TwoUserAuthorities_UserAuthoritiesHaveBeenMappedToCommandsAndReturned() {
        UserAuthority firstUserAuthority = createDefaultUserAuthority();
        UserAuthority secondUserAuthority = createDefaultUserAuthority();
        IdNameCommand firstIdNameCommand = new IdNameCommand(firstUserAuthority,
                UserAuthority::getId, UserAuthority::getName);
        IdNameCommand secondIdNameCommand = new IdNameCommand(secondUserAuthority,
                UserAuthority::getId, UserAuthority::getName);
        List<UserAuthority> givenAuthorities = Arrays.asList(firstUserAuthority, secondUserAuthority);
        when(userAuthorityRepository.findByOrderByNameAsc()).thenReturn(givenAuthorities);

        List<IdNameCommand> actualUserAuthorities = testee.getUserAuthoritiesLinks();

        assertThat(actualUserAuthorities).containsExactly(firstIdNameCommand, secondIdNameCommand);
    }

}