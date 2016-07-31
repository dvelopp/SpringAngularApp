package springAngularApp.users.service

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import springAngularApp.system.domain.model.IdNameCommand
import springAngularApp.users.domain.entities.UserGroup
import springAngularApp.users.domain.entities.UserGroupFixture
import springAngularApp.users.domain.repositories.UserGroupRepository
import springAngularApp.users.service.mapper.UserGroupCommandMapper

import static java.util.Collections.emptyList
import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup
import static springAngularApp.users.domain.model.UserGroupCommandFixture.createDefaultUserGroupCommand

@Title("User group service")
@Narrative("""
As a used I want to be able to view and manage the user groups. So that I need a layer between the domain and
the UI to provide this functionality.
""")
public class UserGroupServiceSpec extends Specification {

    UserGroupRepository userGroupRepository = Mock();
    UserGroupCommandMapper userGroupCommandMapper = Mock();

    UserGroupService testee = new UserGroupService(
            userGroupRepository: userGroupRepository,
            userGroupCommandMapper: userGroupCommandMapper
    );

    def "When service gets user group links and there is nothing found, the empty list will be returned"() {
        given: "user groups don't exist"
        userGroupRepository.findByOrderByNameAsc() >> emptyList();
        when: "get user group links"
        def actualUserGroups = testee.getUserGroupLinks();
        then: "user group links list must be empty"
        actualUserGroups.isEmpty()
    }

    def "When service gets user group links and there are two user groups exist, both will be mapped and returned"() {
        given: "two existing user groups"
        def firstUserGroup = createDefaultUserGroup();
        def secondUserGroup = createDefaultUserGroup();
        def firstIdNameCommand = new IdNameCommand(firstUserGroup, { u -> u.getId() }, { u -> u.getName() });
        def secondIdNameCommand = new IdNameCommand(secondUserGroup, { u -> u.getId() }, { u -> u.getName() });
        userGroupRepository.findByOrderByNameAsc() >> [firstUserGroup, secondUserGroup];
        when: "get user group links"
        def actualUserGroups = testee.getUserGroupLinks();
        then: "two user group links must be returned"
        actualUserGroups.size() == 2
        actualUserGroups.containsAll([firstIdNameCommand, secondIdNameCommand]);
    }

    def "When service gets user groups and there is nothing found, the empty list will be returned"() {
        given: "user groups don't exist"
        userGroupRepository.findByOrderByNameAsc() >> emptyList();
        when: "get user groups"
        def actualUserGroups = testee.getUserGroups();
        then: "user groups list has to be empty"
        actualUserGroups.isEmpty();
    }

    def "When service gets user groups and there are two user groups exist, both will be mapped and returned"() {
        given: "two user group exist"
        def firstUserGroup = createDefaultUserGroup();
        def secondUserGroup = createDefaultUserGroup();
        def firstUserGroupCommand = createDefaultUserGroupCommand();
        def secondUserGroupCommand = createDefaultUserGroupCommand();
        userGroupRepository.findByOrderByNameAsc() >> [firstUserGroup, secondUserGroup];
        userGroupCommandMapper.mapToCommand(firstUserGroup) >> firstUserGroupCommand;
        userGroupCommandMapper.mapToCommand(secondUserGroup) >> secondUserGroupCommand;
        when: "get user groups"
        def actualUserGroups = testee.getUserGroups();
        then: "two user group should be returned"
        actualUserGroups.size() == 2
        actualUserGroups.containsAll([firstUserGroupCommand, secondUserGroupCommand]);
    }

    def "When service saves a valid group, the group is mapped and saved"() {
        given: "valid group"
        def userGroupCommand = createDefaultUserGroupCommand();
        def userGroup = createDefaultUserGroup();
        1 * userGroupCommandMapper.mapFromCommand(userGroupCommand) >> userGroup;
        when: "save"
        testee.save(userGroupCommand);
        then: "user group will be mapped and saved"
        1 * userGroupRepository.save(userGroup);
    }

    def "When service deletes the general group, it delegates deletion to the repository"() {
        given: "general group"
        def userGroup = createDefaultUserGroup();
        userGroupRepository.findOne(userGroup.getId()) >> userGroup;
        when: "delete"
        testee.delete(userGroup.getId());
        then: "group will be deleted"
        1 * userGroupRepository.delete(userGroup.getId());
    }

    def "When service deletes the super user, IllegalArgumentException is thrown"() {
        given: "super user group"
        UserGroup superUserGroup = UserGroupFixture.builder().setSuperUserGroup(true).build();
        userGroupRepository.findOne(superUserGroup.getId()) >> superUserGroup;
        when: "delete"
        testee.delete(superUserGroup.getId());
        then: "exception will be thrown"
        thrown(IllegalArgumentException)
    }

}