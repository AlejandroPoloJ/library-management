package pe.com.apolo.domain.model.book;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book buildBook() {
        return new Book(
                BookId.generate(),
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, 8, 1)
        );
    }

    @Test
    void shouldCreateBookSuccessfully() {

        Book book = buildBook();
        assertAll(
                () -> assertNotNull(book.getId()),
                () -> assertEquals("Clean Code", book.getTitle()),
                () -> assertEquals("Robert C. Martin", book.getAuthor()),
                () -> assertEquals(464, book.getPageCount()),
                () -> assertEquals(
                        LocalDate.of(2008, 8, 1),
                        book.getPublicationDate()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");
        LocalDate publicationDate = LocalDate.now().minusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        null,
                        isbn,
                        "Robert C. Martin",
                        464,
                        publicationDate
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");
        LocalDate publicationDate = LocalDate.now().minusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        "",
                        isbn,
                        "Robert C. Martin",
                        464,
                        publicationDate
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsNull() {
        BookId bookId = BookId.generate();
        LocalDate publicationDate = LocalDate.now().minusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        "Clean Code",
                        null,
                        "Robert C. Martin",
                        464,
                        publicationDate
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNull() {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");
        LocalDate publicationDate = LocalDate.now().minusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        "Clean Code",
                        isbn,
                        null,
                        464,
                        publicationDate
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsBlank() {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");
        LocalDate publicationDate = LocalDate.now().minusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        "Clean Code",
                        isbn,
                        "",
                        464,
                        publicationDate
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenPageCountIsZero() {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");
        LocalDate publicationDate = LocalDate.now().minusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        "Clean Code",
                        isbn,
                        "Robert C. Martin",
                        0,
                        publicationDate
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenPublicationDateIsNull() {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        "Clean Code",
                        isbn,
                        "Robert C. Martin",
                        464,
                        null
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenPublicationDateIsFuture() {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");
        LocalDate publicationDate = LocalDate.now().plusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        "Clean Code",
                        isbn,
                        "Robert C. Martin",
                        464,
                        publicationDate
                )
        );
    }
}
