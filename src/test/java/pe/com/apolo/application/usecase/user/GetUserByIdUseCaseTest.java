package pe.com.apolo.application.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.UserNotFoundException;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByIdUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByIdUseCaseImpl useCase;

    @Test
    void shouldReturnUserWhenExists() {

        UserId userId = UserId.generate();

        User user = new User(
                userId,
                "Alejandro",
                LocalDate.now().minusYears(25),
                true,
                Role.USER,
                "alejandro@test.com",
                "123456"
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        User result = useCase.execute(userId);

        assertEquals(user, result);

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        UserId userId = UserId.generate();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> useCase.execute(userId)
        );

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}