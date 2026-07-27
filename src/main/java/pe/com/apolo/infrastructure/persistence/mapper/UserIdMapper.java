package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

import java.util.UUID;

@Mapper(config = CentralMapperConfig.class)
public interface UserIdMapper {

    default UUID toUuid(UserId id) {
        return id == null ? null : id.getValue();
    }

    default UserId toUserId(UUID id) {
        return id == null ? null : new UserId(id);
    }
}
