package pe.com.apolo.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.user.UserRepository;
import pe.com.apolo.infrastructure.persistence.mapper.UserMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataUserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository repository;
    private final UserMapper mapper;

    public UserRepositoryAdapter(
            SpringDataUserRepository repository,
            UserMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(
                repository.save(
                        mapper.toEntity(user)
                )
        );
    }
}
