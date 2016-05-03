package springAngularApp.users.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class UserCommand {

    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String userGroupId;
    private String userGroupName;
    private String password;
    private Boolean systemUser;

    public UserCommand() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(Boolean systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCommand that = (UserCommand) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(userGroupId, that.userGroupId) &&
                Objects.equals(userGroupName, that.userGroupName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(systemUser, that.systemUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, userGroupId, userGroupName, password, systemUser);
    }

    public boolean isNew (){
        return isBlank(id);
    }
}
