package springAngularApp.users.domain.entities;

import org.apache.commons.lang3.RandomStringUtils;

import static springAngularApp.users.domain.entities.UserGroupFixture.createDefaultUserGroup;

public class UserFixture {

    public static User createDefaultUser() {
        return builder().build();
    }

    public static User createUserWithFirstName(String firstName) {
        return builder().setFirstName(firstName).build();
    }

    public static User createUserWithName(String name) {
        return builder().setName(name).build();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String name = "UserName_" + RandomStringUtils.random(10);
        private String password = "Password";
        private UserGroup group = createDefaultUserGroup();
        private String firstName = "UserFirstName";
        private String lastName = "UserLastName";
        private Boolean systemUser = false;

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setGroup(UserGroup group) {
            this.group = group;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setSystemUser(Boolean systemUser) {
            this.systemUser = systemUser;
            return this;
        }

        public User build() {
            User user = new User(name, password, group);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setSystemUser(systemUser);
            return user;
        }
    }

}