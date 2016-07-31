package springAngularApp.users.service.mapper

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import springAngularApp.system.domain.model.IdNameCommand
import springAngularApp.users.domain.entities.UserAuthority
import springAngularApp.users.domain.entities.UserGroup
import springAngularApp.users.domain.model.UserGroupCommand
import springAngularApp.users.domain.repositories.UserAuthorityRepository
import springAngularApp.users.domain.repositories.UserGroupRepository

import static java.util.Arrays.asList
import static springAngularApp.users.domain.entities.UserAuthorityFixture.createDefaultUserAuthority
import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup
import static springAngularApp.users.domain.model.UserGroupCommandFixture.createDefaultUserGroupCommand
import static springAngularApp.users.domain.model.UserGroupCommandFixture.createUserGroupCommandWithEmptyId
import static springAngularApp.users.service.UserAuthorityService.USER_AUTHORITY_TO_LINK_FUNCTION

@Title("Mapper to map between UserGroup and UserGroupCommand")
@Narrative("""
    As a user I want to be able to save the user data from the UI. So that I need the clear way to transfer state
    between domain objects and their UI representations. Thus, I need a converter to convert the object to/from command.
""")
class UserGroupCommandMapperSpec extends Specification {

    private UserAuthorityRepository userAuthorityRepository = Mock();
    private UserGroupRepository userGroupRepository = Mock();
    private UserGroupCommandMapper testee = new UserGroupCommandMapper(
            userAuthorityRepository: userAuthorityRepository,
            userGroupRepository: userGroupRepository
    )

    def "Mapper maps a valid entity to the command, entity will be mapped to the command"() {
        given: "valid entity"
        UserGroup userGroup = createDefaultUserGroup();
        userGroup.setAuthorities([createDefaultUserAuthority(), createDefaultUserAuthority()]);
        when: "map to command"
        UserGroupCommand command = testee.mapToCommand(userGroup);
        then: "object has to be converted to the command"
        assertMapToCommand(userGroup, command);
    }

    private static void assertMapToCommand(UserGroup userGroup, UserGroupCommand command) {
        assert command.getId() == userGroup.getId();
        assert command.getName() == userGroup.getName();
        assert command.isSuperUserGroup() == userGroup.isSuperUserGroup();
        command.getAuthorities().size().times {
            IdNameCommand userAuthorityCommand = command.getAuthorities()[it];
            UserAuthority userAuthority = userGroup.getAuthorities()[it];
            assert userAuthorityCommand.getId() == userAuthority.getId();
            assert userAuthorityCommand.getName() == userAuthority.getName();
        }
    }

    def "Mapper maps entity from command and entity exists, entity will be modified with values from the command"() {
        given: "valid command for existing user"
        UserGroupCommand command = createDefaultUserGroupCommand();
        userGroupRepository.findOne(command.getId()) >> createDefaultUserGroup();
        mockUserAuthoritiesForMapFromCommand(command);
        when: "map from command"
        UserGroup userGroup = testee.mapFromCommand(command);
        then: "existing user entity must be modified with the new data"
        assertMapFromCommand(command, userGroup);
    }

    def "Mapper maps entity from command and entity does not exist, new entity will be created and mapped from command"() {
        given: "valid command for not existing user"
        UserGroupCommand command = createUserGroupCommandWithEmptyId();
        mockUserAuthoritiesForMapFromCommand(command);
        when: "map from command"
        UserGroup userGroup = testee.mapFromCommand(command);
        then: "new user object must be created with the specified data"
        assertMapFromCommand(command, userGroup);
    }

    private void mockUserAuthoritiesForMapFromCommand(UserGroupCommand command) {
        UserAuthority userAuthority = createDefaultUserAuthority();
        IdNameCommand userAuthorityLink = USER_AUTHORITY_TO_LINK_FUNCTION.apply(userAuthority);
        command.getAuthorities() << userAuthorityLink;
        userAuthorityRepository.findAll([userAuthority.getId()]) >> asList(userAuthority);
    }

    private static void assertMapFromCommand(UserGroupCommand command, UserGroup userGroup) {
        assert userGroup.isSuperUserGroup() == command.isSuperUserGroup();
        assert userGroup.getName() == command.getName();
        userGroup.getAuthorities().size().times {
            def currentUserAuthorityCommand = command.getAuthorities()[it];
            def currentUserAuthority = userGroup.getAuthorities()[it];
            assert currentUserAuthorityCommand.getId() == currentUserAuthority.getId();
        }
    }

}
