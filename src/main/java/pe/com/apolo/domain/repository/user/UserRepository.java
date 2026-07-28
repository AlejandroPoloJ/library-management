package pe.com.apolo.domain.repository.user;

import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);

    List<User> findAll();

    User save(User user);

    Optional<User> findByEmail(String email);
}
