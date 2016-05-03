package springAngularApp.system.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.List;

import static java.util.Collections.emptyList;

@MappedSuperclass
public abstract class Identifiable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String id;

    public String getId() {
        return id;
    }

    /**
     * Can be overridden in the sub classes in order to allow them know about their dependencies
     * @return dependencies of the entity
     */
    public List<Identifiable> getRelations(){
        return emptyList();
    }

}
