package pe.com.apolo.application.usecase.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.repository.user.UserRepository;
import pe.com.apolo.domain.service.JwtService;

public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginUseCaseImpl(
            UserRepository userRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String execute(String email, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return jwtService.generateToken(user);
    }
}
