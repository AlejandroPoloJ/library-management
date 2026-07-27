package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

import java.util.UUID;

@Mapper(config = CentralMapperConfig.class)
public interface LoanIdMapper {

    default UUID toUuid(LoanId id) {
        return id == null ? null : id.getValue();
    }

    default LoanId toLoanId(UUID id) {
        return id == null ? null : new LoanId(id);
    }
}
