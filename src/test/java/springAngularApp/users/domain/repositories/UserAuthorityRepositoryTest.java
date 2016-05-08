package springAngularApp.users.domain.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springAngularApp.system.domain.HibernateIntegrationTest;
import springAngularApp.users.domain.entities.UserAuthority;

import static org.assertj.core.api.Assertions.assertThat;
import static springAngularApp.users.domain.entities.UserAuthorityFixture.createUserAuthorityWithName;

public class UserAuthorityRepositoryTest extends HibernateIntegrationTest<UserAuthority> {

    @Autowired private UserAuthorityRepository userAuthorityRepository;

    @Test
    public void findByOrderByNameAsc_TwoUserAuthorities_TwoUserAuthoritiesHaveBeenReturned(){
        UserAuthority firstAuthority = createUserAuthorityWithName("A");
        UserAuthority secondAuthority = createUserAuthorityWithName("B");
        saveAll(firstAuthority, secondAuthority).flush();

        Iterable<UserAuthority> actualUserAuthorities = userAuthorityRepository.findByOrderByNameAsc();

        assertThat(actualUserAuthorities).hasSize(2);
    }

    @Test
    public void findByOrderByNameAsc_TwoUserAuthoritiesSavedInWrongOrder_UserAuthoritiesHaveBeenReturnedInTheCorrectOrder(){
        UserAuthority firstAuthority = createUserAuthorityWithName("B");
        UserAuthority secondAuthority = createUserAuthorityWithName("A");
        saveAll(firstAuthority, secondAuthority).flush();

        Iterable<UserAuthority> actualUserAuthorities = userAuthorityRepository.findByOrderByNameAsc();

        assertThat(actualUserAuthorities).containsExactly(secondAuthority, firstAuthority);
    }

    @Test
    public void findByOrderByNameAsc_TwoUserAuthoritiesSavedInCorrectOrder_UserAuthoritiesHaveBeenReturnedInTheCorrectOrder(){
        UserAuthority firstAuthority = createUserAuthorityWithName("A");
        UserAuthority secondAuthority = createUserAuthorityWithName("B");
        saveAll(firstAuthority, secondAuthority).flush();

        Iterable<UserAuthority> actualUserAuthorities = userAuthorityRepository.findByOrderByNameAsc();

        assertThat(actualUserAuthorities).containsExactly(firstAuthority, secondAuthority);
    }

    @Override
    protected Class<UserAuthority> getEntityClass(){
        return UserAuthority.class;
    }


}