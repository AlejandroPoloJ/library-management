package pe.com.apolo.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.infrastructure.web.dto.response.BookResponse;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {

    @Mapping(target = "id", expression = "java(book.getId().getValue())")
    @Mapping(target = "isbn", expression = "java(book.getIsbn().getValue())")
    BookResponse toResponse(Book book);

}