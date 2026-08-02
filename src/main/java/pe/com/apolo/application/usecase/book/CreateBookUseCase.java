package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.book.Book;

public interface CreateBookUseCase {

    Book execute(Book book);

}