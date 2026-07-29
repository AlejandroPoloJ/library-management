package pe.com.apolo.infrastructure.persistence.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.persistence.entity.UserEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void shouldMapEntityToDomain() {

        UUID id = UUID.randomUUID();

        UserEntity entity = new UserEntity(
                id,
                "Alejandro",
                LocalDate.of(1998, Month.JANUARY, 15),
                true,
                Role.USER,
                "alejandro@test.com",
                "password"
        );

        User user = mapper.toDomain(entity);

        assertAll(
                () -> assertEquals(id, user.getId().getValue()),
                () -> assertEquals("Alejandro", user.getFullName()),
                () -> assertEquals(LocalDate.of(1998, Month.JANUARY, 15), user.getBirthDate()),
                () -> assertTrue(user.isActive()),
                () -> assertEquals(Role.USER, user.getRole()),
                () -> assertEquals("alejandro@test.com", user.getEmail()),
                () -> assertEquals("password", user.getPassword())
        );
    }

    @Test
    void shouldMapDomainToEntity() {

        UUID id = UUID.randomUUID();

        User user = new User(
                new UserId(id),
                "Alejandro",
                LocalDate.of(1998, Month.JANUARY, 15),
                true,
                Role.ADMIN,
                "admin@test.com",
                "encoded-password"
        );

        UserEntity entity = mapper.toEntity(user);

        assertAll(
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals("Alejandro", entity.getFullName()),
                () -> assertEquals(LocalDate.of(1998, Month.JANUARY, 15), entity.getBirthDate()),
                () -> assertTrue(entity.isActive()),
                () -> assertEquals(Role.ADMIN, entity.getRole()),
                () -> assertEquals("admin@test.com", entity.getEmail()),
                () -> assertEquals("encoded-password", entity.getPassword())
        );
    }
}