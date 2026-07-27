package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface ISBNMapper {

    default String toString(ISBN isbn) {
        return isbn == null ? null : isbn.getValue();
    }

    default ISBN toISBN(String value) {
        return value == null ? null : new ISBN(value);
    }
}
