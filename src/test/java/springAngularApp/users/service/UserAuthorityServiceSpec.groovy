package springAngularApp.users.service

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import springAngularApp.system.domain.model.IdNameCommand
import springAngularApp.users.domain.repositories.UserAuthorityRepository

import static springAngularApp.users.domain.entities.UserAuthorityFixture.createDefaultUserAuthority

@Title("User authority service")
@Narrative("""
As a used I want to be able to view and manage the user authorities. So that I need a layer between the domain and
the UI to provide this functionality.
""")
class UserAuthorityServiceSpec extends Specification {

    UserAuthorityRepository userAuthorityRepository = Mock();
    UserAuthorityService testee = new UserAuthorityService(
            userAuthorityRepository: userAuthorityRepository
    );

    def "When service gets user authorities and nothing is found, empty list will be returned"() {
        given: "no authorities exist"
        userAuthorityRepository.findByOrderByNameAsc() >> [];
        when: "get authorities links"
        def actualUserAuthorities = testee.getUserAuthoritiesLinks();
        then: "authorities links must be empty"
        actualUserAuthorities.isEmpty();
    }

    def "When service gets the user authority links and two links exist, two links will be mapped and returned"() {
        given: "two existing links"
        def firstUserAuthority = createDefaultUserAuthority();
        def secondUserAuthority = createDefaultUserAuthority();
        def firstIdNameCommand = new IdNameCommand(firstUserAuthority, { u -> u.getId() }, { u -> u.getName() });
        def secondIdNameCommand = new IdNameCommand(secondUserAuthority, { u -> u.getId() }, { u -> u.getName() });
        def givenAuthorities = [firstUserAuthority, secondUserAuthority];
        userAuthorityRepository.findByOrderByNameAsc() >> givenAuthorities;
        when: "get authorities links"
        def actualUserAuthorities = testee.getUserAuthoritiesLinks();
        then: "two links must be returned"
        actualUserAuthorities.size() == 2
        actualUserAuthorities.containsAll([firstIdNameCommand, secondIdNameCommand]);
    }

}
