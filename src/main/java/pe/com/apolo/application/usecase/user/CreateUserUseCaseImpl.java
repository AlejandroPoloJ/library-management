package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.repository.user.UserRepository;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository repository;

    public CreateUserUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(User user) {
        repository.save(user);
    }
}
