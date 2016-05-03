package springAngularApp.users.domain.entities;

import org.springframework.util.Assert;
import springAngularApp.system.domain.entities.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user_group")
@Entity
public class UserGroup extends Identifiable {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "userGroup_userAuthority",
            joinColumns = {@JoinColumn(name = "userGroup_id")},
            inverseJoinColumns = {@JoinColumn(name = "userAuthority_id")}
    )
    private List<UserAuthority> authorities = new ArrayList<>();

    public UserGroup(String name) {
        Assert.hasText(name);
        this.name = name;
    }

    private UserGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.hasText(name);
        this.name = name;
    }

    public List<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public UserGroup(String name, List<UserAuthority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

}
