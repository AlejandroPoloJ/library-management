package pe.com.apolo.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void shouldLoadUserSuccessfully() {

        User user = buildUser();

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        CustomUserDetails result = (CustomUserDetails)
                service.loadUserByUsername(user.getEmail());

        assertAll(
                () -> assertEquals(user.getEmail(), result.getUsername()),
                () -> assertEquals(user.getPassword(), result.getPassword()),
                () -> assertTrue(result.isEnabled()),
                () -> assertEquals(user, result.getUser())
        );

        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("test@test.com")
        );

        verify(userRepository).findByEmail("test@test.com");
    }

    private User buildUser() {

        return new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.of(1998, Month.JANUARY, 15),
                true,
                Role.ADMIN,
                "admin@apolo.com",
                "123456"
        );
    }
}