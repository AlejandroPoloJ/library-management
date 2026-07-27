package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.infrastructure.persistence.entity.FineEntity;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {
                FineIdMapper.class,
                LoanMapper.class
        },
        imports = java.util.Currency.class
)
public interface FineMapper {

    @Mapping(
            target = "money",
            expression = "java(new Money(entity.getAmount(), Currency.getInstance(entity.getCurrency())))"
    )
    Fine toDomain(FineEntity entity);

    @Mapping(target = "amount", expression = "java(fine.getMoney().getAmount())")
    @Mapping(target = "currency", expression = "java(fine.getMoney().getCurrency().getCurrencyCode())")
    FineEntity toEntity(Fine fine);
}