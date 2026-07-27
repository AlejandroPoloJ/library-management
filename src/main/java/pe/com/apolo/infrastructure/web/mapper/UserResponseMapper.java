package pe.com.apolo.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.infrastructure.web.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    default UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId().getValue(),
                user.getFullName(),
                user.getBirthDate(),
                user.isActive()
        );
    }

}