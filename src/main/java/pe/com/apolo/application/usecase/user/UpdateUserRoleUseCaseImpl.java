package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.exception.UserNotFoundException;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.user.UserRepository;

public class UpdateUserRoleUseCaseImpl implements UpdateUserRoleUseCase {

    private final UserRepository userRepository;

    public UpdateUserRoleUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UserId userId, Role role) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.changeRole(role);

        userRepository.save(user);
    }
}
