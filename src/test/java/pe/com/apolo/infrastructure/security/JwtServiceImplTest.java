package pe.com.apolo.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.config.JwtProperties;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {

        JwtProperties properties = new JwtProperties(
                "12345678901234567890123456789012",
                3600000
        );

        jwtService = new JwtServiceImpl(properties);
    }

    @Test
    void shouldGenerateValidToken() {

        User user = buildUser();

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractUsername() {

        User user = buildUser();

        String token = jwtService.generateToken(user);

        String username = jwtService.extractUsername(token);

        assertEquals(user.getEmail(), username);
    }

    @Test
    void shouldReturnTrueWhenTokenIsValid() {

        String token = jwtService.generateToken(buildUser());

        assertTrue(jwtService.isValid(token));
    }

    @Test
    void shouldReturnFalseWhenTokenIsInvalid() {

        assertFalse(jwtService.isValid("invalid-token"));
    }

    @Test
    void shouldReturnFalseWhenTokenIsEmpty() {

        assertFalse(jwtService.isValid(""));
    }

    @Test
    void shouldThrowExceptionWhenExtractingUsernameFromInvalidToken() {

        assertThrows(
                Exception.class,
                () -> jwtService.extractUsername("invalid-token")
        );
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