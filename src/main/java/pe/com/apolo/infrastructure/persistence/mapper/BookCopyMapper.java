package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.infrastructure.persistence.entity.BookCopyEntity;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

@Mapper(
        config = CentralMapperConfig.class,
        uses = BookCopyIdMapper.class
)
public interface BookCopyMapper {

    BookCopy toDomain(BookCopyEntity entity);

    @Mapping(target = "book", ignore = true)
    BookCopyEntity toEntity(BookCopy domain);

}
