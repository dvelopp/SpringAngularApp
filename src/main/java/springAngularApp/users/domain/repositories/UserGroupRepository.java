package springAngularApp.users.domain.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springAngularApp.users.domain.entities.UserGroup;

import java.util.List;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, String> {

    List<UserGroup> findByOrderByNameAsc();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM UserGroup c WHERE c.name = :name")
    Boolean existsByName(@Param("name") String name);

}
