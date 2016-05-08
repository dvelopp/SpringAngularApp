package springAngularApp.users.domain.model;

import org.apache.commons.lang3.RandomStringUtils;
import springAngularApp.system.domain.model.IdNameCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserGroupCommandFixture {

    public static UserGroupCommand createUserGroupCommandWithEmptyId() {
        return builder().setId(null).build();
    }

    public static UserGroupCommand createDefaultUserGroupCommand() {
        return builder().build();
    }

    public static UserGroupCommandBuilder builder() {
        return new UserGroupCommandBuilder();
    }

    public static class UserGroupCommandBuilder {

        private String name = "UserGroupCommandName_" + RandomStringUtils.random(10);
        private String id = UUID.randomUUID().toString();
        private boolean superUserGroup = false;
        private List<IdNameCommand> authorities = new ArrayList<>();

        public UserGroupCommand build() {
            UserGroupCommand command = new UserGroupCommand();
            command.setName(name);
            command.setId(id);
            command.setSuperUserGroup(superUserGroup);
            command.setAuthorities(authorities);
            return command;
        }

        public UserGroupCommandBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserGroupCommandBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public UserGroupCommandBuilder setSuperUserGroup(boolean superUserGroup) {
            this.superUserGroup = superUserGroup;
            return this;
        }

        public UserGroupCommandBuilder setAuthorities(List<IdNameCommand> authorities) {
            this.authorities = authorities;
            return this;
        }
    }

}