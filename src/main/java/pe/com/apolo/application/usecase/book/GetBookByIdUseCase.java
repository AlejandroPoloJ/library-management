package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;

public interface GetBookByIdUseCase {

    Book execute(BookId id);

}