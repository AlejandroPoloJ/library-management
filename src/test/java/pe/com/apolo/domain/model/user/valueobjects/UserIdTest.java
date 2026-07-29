package pe.com.apolo.domain.model.user.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    void shouldCreateUserIdSuccessfully() {
        UUID uuid = UUID.randomUUID();
        UserId userId = new UserId(uuid);
        assertEquals(uuid, userId.value());
        assertEquals(uuid, userId.getValue());
    }

    @Test
    void shouldGenerateRandomUserId() {
        assertNotNull(UserId.generate());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new UserId(null));
        assertEquals("UserId cannot be null.", exception.getMessage());
    }
}