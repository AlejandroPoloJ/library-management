package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

import java.util.UUID;

@Mapper(config = CentralMapperConfig.class)
public interface BookCopyIdMapper {

    default UUID toUuid(BookCopyId id) {
        return id == null ? null : id.getValue();
    }

    default BookCopyId toBookCopyId(UUID id) {
        return id == null ? null : new BookCopyId(id);
    }
}