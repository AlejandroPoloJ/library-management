package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.infrastructure.persistence.entity.UserEntity;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

@Mapper(
        config = CentralMapperConfig.class,
        uses = UserIdMapper.class
)
public interface UserMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);
}