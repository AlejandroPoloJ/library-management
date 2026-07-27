package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.exception.UserNotFoundException;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.user.UserRepository;

public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    private final UserRepository repository;

    public GetUserByIdUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User execute(UserId id) {

        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
