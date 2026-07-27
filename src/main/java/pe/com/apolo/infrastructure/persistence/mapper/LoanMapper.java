package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.infrastructure.persistence.entity.LoanEntity;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {
                LoanIdMapper.class,
                UserMapper.class,
                BookCopyMapper.class
        }
)
public interface LoanMapper {

    Loan toDomain(LoanEntity entity);

    LoanEntity toEntity(Loan loan);
}