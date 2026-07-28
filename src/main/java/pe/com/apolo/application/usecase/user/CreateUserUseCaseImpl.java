package pe.com.apolo.application.usecase.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.repository.user.UserRepository;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCaseImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(User user) {
        String encoded = passwordEncoder.encode(user.getPassword());
        User securedUser = new User(
                user.getId(),
                user.getFullName(),
                user.getBirthDate(),
                user.isActive(),
                user.getRole(),
                user.getEmail(),
                encoded
        );

        userRepository.save(securedUser);
    }
}
