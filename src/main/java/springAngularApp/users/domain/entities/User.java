package springAngularApp.users.domain.entities;

import org.springframework.util.Assert;
import springAngularApp.system.domain.entities.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "user_user")
public class User extends Identifiable {

    @Transient public static final String OLD_PASSWORD_MASK = "******";
    @Transient public static final int MIN_PASSWORD_LENGTH = 6;

    private String firstName;
    private String lastName;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean systemUser;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "user_user_user_group"), nullable = false)
    private UserGroup group;

    public User(String name, String password, UserGroup group) {
        Assert.hasText(name);
        Assert.hasText(password);
        Assert.notNull(group);
        this.name = name;
        this.password = password;
        this.group = group;
    }

    public User(String firstName, String lastName, String name, String password, Boolean systemUser, UserGroup group) {
        this(name, password, group);
        this.firstName = firstName;
        this.lastName = lastName;
        this.systemUser = systemUser;
    }

    private User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.hasText(name);
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Assert.hasText(password);
        this.password = password;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        Assert.notNull(group);
        this.group = group;
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

    public boolean isSystemUser() {
        return systemUser;
    }

    public void setSystemUser(boolean systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public List<Identifiable> getRelations() {
        List<Identifiable> relations = new ArrayList<>();
        relations.add(this.getGroup());
        return relations;
    }
}
