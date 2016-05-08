package springAngularApp.users.domain.entities;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class UserGroupFixture {

    public static UserGroup createDefaultUserGroup() {
        return builder().build();
    }

    public static UserGroup createUserGroupWithName(String name) {
        return builder().setName(name).build();
    }

    public static UserGroupBuilder builder() {
        return new UserGroupBuilder();
    }

    public static class UserGroupBuilder {
        private String name = "UserGroupName_" + RandomStringUtils.random(10);
        private List<UserAuthority> authorities = new ArrayList<>();
        private boolean superUserGroup;

        public UserGroupBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserGroupBuilder setAuthorities(List<UserAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserGroupBuilder setSuperUserGroup(boolean superUserGroup) {
            this.superUserGroup = superUserGroup;
            return this;
        }

        public UserGroup build() {
            UserGroup userGroup = new UserGroup(name);
            userGroup.setAuthorities(authorities);
            userGroup.setSuperUserGroup(superUserGroup);
            return userGroup;
        }
    }

}