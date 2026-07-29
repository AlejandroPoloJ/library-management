package pe.com.apolo.infrastructure.persistence.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.persistence.entity.UserEntity;
import pe.com.apolo.infrastructure.persistence.mapper.UserMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataUserRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private SpringDataUserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserRepositoryAdapter adapter;

    private User buildUser(UUID id) {
        return new User(
                new UserId(id),
                "Juan Pérez",
                LocalDate.of(1990, Month.JANUARY, 1),
                true,
                Role.USER,
                "juan@apolo.com",
                "123456"
        );
    }

    private UserEntity buildEntity(UUID id) {
        return new UserEntity(
                id,
                "Juan Pérez",
                LocalDate.of(1990, Month.JANUARY, 1),
                true,
                Role.USER,
                "juan@apolo.com",
                "123456"
        );
    }

    @Test
    void shouldFindUserById() {
        UUID id = UUID.randomUUID();
        UserEntity entity = buildEntity(id);
        User domain = buildUser(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<User> result = adapter.findById(new UserId(id));

        assertThat(result).contains(domain);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundById() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = adapter.findById(new UserId(id));

        assertThat(result).isEmpty();
        verify(mapper, never()).toDomain(any(UserEntity.class));
    }

    @Test
    void shouldFindAllUsers() {
        UUID id = UUID.randomUUID();
        UserEntity entity = buildEntity(id);
        User domain = buildUser(id);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<User> result = adapter.findAll();

        assertThat(result).containsExactly(domain);
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(repository.findAll()).thenReturn(List.of());

        List<User> result = adapter.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveUser() {
        UUID id = UUID.randomUUID();
        User domain = buildUser(id);
        UserEntity entity = buildEntity(id);
        UserEntity savedEntity = buildEntity(id);
        User savedDomain = buildUser(id);

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedDomain);

        User result = adapter.save(domain);

        assertThat(result).isEqualTo(savedDomain);
        verify(repository).save(entity);
    }

    @Test
    void shouldFindUserByEmail() {
        UUID id = UUID.randomUUID();
        String email = "juan@apolo.com";
        UserEntity entity = buildEntity(id);
        User domain = buildUser(id);

        when(repository.findByEmail(email)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<User> result = adapter.findByEmail(email);

        assertThat(result).contains(domain);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        String email = "notfound@apolo.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = adapter.findByEmail(email);

        assertThat(result).isEmpty();
        verify(mapper, never()).toDomain(any(UserEntity.class));
    }
}