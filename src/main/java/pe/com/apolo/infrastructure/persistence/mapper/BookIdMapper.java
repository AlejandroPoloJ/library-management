package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

import java.util.UUID;

@Mapper(config = CentralMapperConfig.class)
public interface BookIdMapper {

    default UUID toUuid(BookId id) {
        return id == null ? null : id.getValue();
    }

    default BookId toBookId(UUID id) {
        return id == null ? null : new BookId(id);
    }

}
