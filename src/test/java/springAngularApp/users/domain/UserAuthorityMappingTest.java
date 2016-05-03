package springAngularApp.users.domain;

import springAngularApp.system.domain.MappingTest;
import springAngularApp.users.domain.entities.UserAuthority;

import static org.assertj.core.api.Assertions.assertThat;
import static springAngularApp.users.domain.entities.UserAuthorityFixture.createDefaultUserAuthority;

public class UserAuthorityMappingTest extends MappingTest<UserAuthority> {

    @Override
    protected UserAuthority getEntityToSave() {
        return createDefaultUserAuthority();
    }

    @Override
    protected void performChecks(UserAuthority expected, UserAuthority actual) {
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getId()).isEqualTo(expected.getId());
    }

    @Override
    protected Class<UserAuthority> getEntityClass() {
        return UserAuthority.class;
    }
}
