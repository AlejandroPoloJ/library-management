package pe.com.apolo.application.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUsersUseCaseImpl useCase;

    @Test
    void shouldReturnAllUsers() {

        List<User> users = List.of(
                new User(
                        UserId.generate(),
                        "Alejandro",
                        LocalDate.now().minusYears(25),
                        true,
                        Role.USER,
                        "alejandro@test.com",
                        "123456"
                ),
                new User(
                        UserId.generate(),
                        "Admin",
                        LocalDate.now().minusYears(30),
                        true,
                        Role.ADMIN,
                        "admin@test.com",
                        "123456"
                )
        );

        when(userRepository.findAll())
                .thenReturn(users);

        List<User> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals(users, result);

        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }
}