package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

import java.util.UUID;

@Mapper(config = CentralMapperConfig.class)
public interface FineIdMapper {

    default UUID toUuid(FineId id) {
        return id == null ? null : id.getValue();
    }

    default FineId toFineId(UUID id) {
        return id == null ? null : new FineId(id);
    }
}