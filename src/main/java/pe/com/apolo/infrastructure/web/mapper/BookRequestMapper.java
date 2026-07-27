package pe.com.apolo.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.infrastructure.web.dto.request.CreateBookRequest;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {

    default Book toDomain(CreateBookRequest request) {

        return new Book(
                BookId.generate(),
                request.title(),
                new ISBN(request.isbn()),
                request.author(),
                request.pageCount(),
                request.publicationDate()
        );
    }

}