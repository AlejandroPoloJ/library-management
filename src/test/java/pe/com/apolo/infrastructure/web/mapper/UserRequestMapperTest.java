package pe.com.apolo.infrastructure.web.mapper;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.web.dto.request.CreateUserRequest;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestMapperTest {

    private final UserRequestMapper mapper = new UserRequestMapper() {};

    @Test
    void shouldMapRequestToDomain() {
        CreateUserRequest request = new CreateUserRequest(
                "Juan Pérez", LocalDate.of(1990, Month.JANUARY, 1), "juan@apolo.com", "123456"
        );

        User user = mapper.toDomain(request);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getFullName()).isEqualTo("Juan Pérez");
        assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1990, Month.JANUARY, 1));
        assertThat(user.isActive()).isTrue();
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getEmail()).isEqualTo("juan@apolo.com");
        assertThat(user.getPassword()).isEqualTo("123456");
    }

    @Test
    void shouldMapUuidToUserId() {
        UUID id = UUID.randomUUID();

        UserId result = mapper.toUserId(id);

        assertThat(result).isEqualTo(new UserId(id));
    }
}