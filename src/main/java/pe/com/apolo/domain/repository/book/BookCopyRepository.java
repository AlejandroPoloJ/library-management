package pe.com.apolo.domain.repository.book;

import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.domain.model.book.valueobjects.BookId;

import java.util.Optional;

public interface BookCopyRepository {

    Optional<BookCopy> findById(BookCopyId id);

    Optional<BookCopy> findAvailableByBookId(BookId bookId);

    BookCopy save(BookCopy copy);

    BookCopy create(BookId bookId);
}