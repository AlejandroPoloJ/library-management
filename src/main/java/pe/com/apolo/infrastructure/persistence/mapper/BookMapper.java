package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.infrastructure.persistence.entity.BookEntity;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

@Mapper(
        config = CentralMapperConfig.class,
        uses = {
                BookIdMapper.class,
                ISBNMapper.class
        }
)
public interface BookMapper {

    Book toDomain(BookEntity entity);

    BookEntity toEntity(Book domain);

}