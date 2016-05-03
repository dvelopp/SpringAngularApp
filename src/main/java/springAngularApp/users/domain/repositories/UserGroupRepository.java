package springAngularApp.users.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springAngularApp.users.domain.entities.UserGroup;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, String> {

}
