package springAngularApp.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springAngularApp.system.domain.model.IdNameCommand;
import springAngularApp.users.domain.entities.UserAuthority;
import springAngularApp.users.domain.repositories.UserAuthorityRepository;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class UserAuthorityService {

    @Autowired private UserAuthorityRepository userAuthorityRepository;

    public static final Function<UserAuthority, IdNameCommand> USER_AUTHORITY_TO_LINK_FUNCTION =
            userAuthority -> new IdNameCommand(userAuthority, UserAuthority::getId, UserAuthority::getName);

    @Transactional(readOnly = true)
    public List<IdNameCommand> getUserAuthoritiesLinks() {
        return userAuthorityRepository.findByOrderByNameAsc().stream()
                .map(USER_AUTHORITY_TO_LINK_FUNCTION)
                .collect(toList());
    }

}
