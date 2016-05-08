package springAngularApp.users.domain.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springAngularApp.system.domain.HibernateIntegrationTest;
import springAngularApp.users.domain.entities.UserGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static springAngularApp.users.domain.entities.UserGroupFixture.createUserGroupWithName;

public class UserGroupRepositoryTest extends HibernateIntegrationTest<UserGroup> {

    @Autowired private UserGroupRepository userGroupRepository;

    @Test
    public void findByOrderByNameAsc_TwoUserGroups_TwoUserGroupsHaveBeenReturned(){
        UserGroup firstUserGroup = createUserGroupWithName("A");
        UserGroup secondUserGroup = createUserGroupWithName("B");
        saveAll(firstUserGroup, secondUserGroup).flush();

        Iterable<UserGroup> actualUserGroups = userGroupRepository.findByOrderByNameAsc();

        assertThat(actualUserGroups).hasSize(2);
    }

    @Test
    public void findByOrderByNameAsc_TwoUserGroupsSavedInWrongOrder_UserGroupsHaveBeenReturnedInTheCorrectOrder(){
        UserGroup firstUserGroup = createUserGroupWithName("B");
        UserGroup secondUserGroup = createUserGroupWithName("A");
        saveAll(firstUserGroup, secondUserGroup).flush();

        Iterable<UserGroup> actualUserGroups = userGroupRepository.findByOrderByNameAsc();

        assertThat(actualUserGroups).containsExactly(secondUserGroup, firstUserGroup);
    }

    @Test
    public void findByOrderByNameAsc_TwoUserGroupsSavedInCorrectOrder_UserGroupsHaveBeenReturnedInTheCorrectOrder(){
        UserGroup firstUserGroup = createUserGroupWithName("A");
        UserGroup secondUserGroup = createUserGroupWithName("B");
        saveAll(firstUserGroup, secondUserGroup).flush();

        Iterable<UserGroup> actualUserGroups = userGroupRepository.findByOrderByNameAsc();

        assertThat(actualUserGroups).containsExactly(firstUserGroup, secondUserGroup);
    }

    @Test
    public void existsByName_UserGroupWithSelectedNameExists_TrueHasBeenReturned(){
        UserGroup userGroup = createUserGroupWithName("Test User Group Name");
        saveAll(userGroup).flush();

        Boolean userExists = userGroupRepository.existsByName(userGroup.getName());

        assertThat(userExists).isTrue();
    }

    @Test
    public void existsByName_UserGroupWithSelectedNameDoesNotExist_FalseHasBeenReturned(){
        UserGroup userGroup = createUserGroupWithName("Test User Group Name");
        saveAll(userGroup).flush();

        Boolean userExists = userGroupRepository.existsByName("Invalid User Group Name");

        assertThat(userExists).isFalse();
    }

    @Test
    public void existsByName_NoUserGroupsExist_FalseHasBeenReturned(){
        Boolean userGroupExists = userGroupRepository.existsByName("Invalid User Group Name");

        assertThat(userGroupExists).isFalse();
    }

    @Override
    protected Class<UserGroup> getEntityClass() {
        return UserGroup.class;
    }
}