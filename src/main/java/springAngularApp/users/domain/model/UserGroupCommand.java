package springAngularApp.users.domain.model;

import springAngularApp.system.domain.model.IdNameCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class UserGroupCommand {

    private String name;
    private String id;
    private boolean superUserGroup;
    private List<IdNameCommand> authorities = new ArrayList<>();

    public UserGroupCommand() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<IdNameCommand> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<IdNameCommand> authorities) {
        this.authorities = authorities;
    }

    public boolean isSuperUserGroup() {
        return superUserGroup;
    }

    public void setSuperUserGroup(boolean superUserGroup) {
        this.superUserGroup = superUserGroup;
    }

    public boolean isNew (){
        return isBlank(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupCommand command = (UserGroupCommand) o;
        return Objects.equals(superUserGroup, command.superUserGroup) &&
                Objects.equals(name, command.name) &&
                Objects.equals(id, command.id) &&
                Objects.equals(authorities, command.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, superUserGroup, authorities);
    }
}
