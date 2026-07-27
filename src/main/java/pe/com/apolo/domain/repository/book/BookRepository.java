package pe.com.apolo.domain.repository.book;

import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(BookId id);
    List<Book> findAll();
    Book save(Book book);
}