package springAngularApp.users.domain.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springAngularApp.system.domain.HibernateIntegrationTest;
import springAngularApp.users.domain.entities.UserAuthority;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static springAngularApp.users.domain.entities.UserAuthorityFixture.createDefaultUserAuthority;

public class UserAuthorityRepositoryTest extends HibernateIntegrationTest<UserAuthority> {

    @Autowired private UserAuthorityRepository userAuthorityRepository;

    @Test
    public void save_ValidEntity_EntityWasSaved() {
        UserAuthority expectedUserAuthority = createDefaultUserAuthority();

        userAuthorityRepository.save(expectedUserAuthority);

        UserAuthority actualUserAuthority = getById(expectedUserAuthority.getId());
        assertThat(actualUserAuthority).isEqualTo(expectedUserAuthority);
    }

    @Test
    public void getById_ValidId_EntityHasBeenReturned(){
        UserAuthority expectedUserAuthority = createDefaultUserAuthority();
        save(expectedUserAuthority);

        UserAuthority actualUserAuthority = userAuthorityRepository.findOne(expectedUserAuthority.getId());

        assertThat(actualUserAuthority).isEqualTo(expectedUserAuthority);
    }

    @Test
    public void getById_InvalidId_EntityHasBeenReturned(){
        UserAuthority expectedUserAuthority = createDefaultUserAuthority();
        save(expectedUserAuthority);

        UserAuthority actualUserAuthority = userAuthorityRepository.findOne(UUID.randomUUID().toString());

        assertThat(actualUserAuthority).isNull();
    }

    @Override
    protected Class<UserAuthority> getEntityClass(){
        return UserAuthority.class;
    }


}