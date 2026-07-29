package pe.com.apolo.infrastructure.web.mapper;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.web.dto.response.UserResponse;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseMapperTest {

    private final UserResponseMapper mapper = new UserResponseMapper() {};

    @Test
    void shouldMapUserToResponse() {
        UUID id = UUID.randomUUID();
        User user = new User(
                new UserId(id), "Juan Pérez", LocalDate.of(1990, Month.JANUARY, 1),
                true, Role.USER, "juan@apolo.com", "123456"
        );

        UserResponse response = mapper.toResponse(user);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.fullName()).isEqualTo("Juan Pérez");
        assertThat(response.birthDate()).isEqualTo(LocalDate.of(1990, Month.JANUARY, 1));
        assertThat(response.active()).isTrue();
    }
}