package pe.com.apolo.application.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.user.UserRepository;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl useCase;

    @Test
    void shouldCreateUser() {

        User user = new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.now().minusYears(25),
                true,
                Role.USER,
                "alejandro@test.com",
                "123456"
        );

        when(passwordEncoder.encode("123456"))
                .thenReturn("encoded-password");

        useCase.execute(user);

        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }
}