package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.book.Book;

import java.util.List;

public interface GetAllBooksUseCase {

    List<Book> execute();

}
