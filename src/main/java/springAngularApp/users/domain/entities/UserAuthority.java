package springAngularApp.users.domain.entities;

import org.springframework.util.Assert;
import springAngularApp.system.domain.entities.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "user_authority")
@Entity
public class UserAuthority extends Identifiable {

    @Column(nullable = false, unique = true)
    private String name;

    private UserAuthority() {
    }

    public UserAuthority(String name) {
        Assert.hasText(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.hasText(name);
        this.name = name;
    }
}
