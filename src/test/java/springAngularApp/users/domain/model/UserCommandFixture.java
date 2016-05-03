package springAngularApp.users.domain.model;

import java.util.UUID;

public class UserCommandFixture {

    public static UserCommand createUserCommandWithEmptyId() {
        return builder().setId(null).build();
    }

    public static UserCommand createDefaultUserCommand() {
        return builder().build();
    }

    public static UserCommandBuilder builder() {
        return new UserCommandBuilder();
    }

    public static class UserCommandBuilder {

        private String id = UUID.randomUUID().toString();
        private String userName = "userName";
        private String firstName = "firstName";
        private String lastName = "lastName";
        private String userGroupId = "userGroupId";
        private String userGroupName = "userGroupName";
        private String password = "password";
        private Boolean systemUser = false;

        public UserCommand build() {
            UserCommand userCommand = new UserCommand();
            userCommand.setId(id);
            userCommand.setUserName(userName);
            userCommand.setFirstName(firstName);
            userCommand.setLastName(lastName);
            userCommand.setUserGroupId(userGroupId);
            userCommand.setUserGroupName(userGroupName);
            userCommand.setPassword(password);
            userCommand.setSystemUser(systemUser);
            return userCommand;
        }

        public UserCommandBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public UserCommandBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserCommandBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserCommandBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserCommandBuilder setUserGroupId(String userGroupId) {
            this.userGroupId = userGroupId;
            return this;
        }

        public UserCommandBuilder setUserGroupName(String userGroupName) {
            this.userGroupName = userGroupName;
            return this;
        }

        public UserCommandBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserCommandBuilder setSystemUser(Boolean systemUser) {
            this.systemUser = systemUser;
            return this;
        }
    }

}