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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserRoleUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserRoleUseCaseImpl useCase;

    private User buildUser() {
        return new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.now().minusYears(25),
                true,
                Role.USER,
                "alejandro@test.com",
                "123456"
        );
    }

    @Test
    void shouldUpdateUserRole() {

        User user = buildUser();

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(user);

        useCase.execute(user.getId(), Role.ADMIN);

        assertEquals(Role.ADMIN, user.getRole());

        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        UserId userId = UserId.generate();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> useCase.execute(userId, Role.ADMIN)
        );

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
        verifyNoMoreInteractions(userRepository);
    }
}