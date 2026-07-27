package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.repository.user.UserRepository;

import java.util.List;

public class GetAllUsersUseCaseImpl
        implements GetAllUsersUseCase {

    private final UserRepository repository;

    public GetAllUsersUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> execute() {
        return repository.findAll();
    }
}
