package pe.com.apolo.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.web.dto.request.CreateUserRequest;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    default User toDomain(CreateUserRequest request) {

        return new User(
                UserId.generate(),
                request.fullName(),
                request.birthDate(),
                true,
                Role.USER,
                request.email(),
                request.password()
        );
    }

    default UserId toUserId(UUID id) {
        return new UserId(id);
    }

}