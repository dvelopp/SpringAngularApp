package springAngularApp.users.ws.schema;

import springAngularApp.users.domain.model.UserGroupCommand;

import java.util.List;

public class UserGroupConfigurationModelResponse {

    private List<UserGroupCommand> userGroups;
    boolean hasUserGroupDeleteAccess;
    boolean hasUserGroupEditAccess;

    public UserGroupConfigurationModelResponse() {
    }

    public List<UserGroupCommand> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroupCommand> userGroups) {
        this.userGroups = userGroups;
    }

    public boolean isHasUserGroupDeleteAccess() {
        return hasUserGroupDeleteAccess;
    }

    public void setHasUserGroupDeleteAccess(boolean hasUserGroupDeleteAccess) {
        this.hasUserGroupDeleteAccess = hasUserGroupDeleteAccess;
    }

    public boolean isHasUserGroupEditAccess() {
        return hasUserGroupEditAccess;
    }

    public void setHasUserGroupEditAccess(boolean hasUserGroupEditAccess) {
        this.hasUserGroupEditAccess = hasUserGroupEditAccess;
    }
}
