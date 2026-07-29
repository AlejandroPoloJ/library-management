package pe.com.apolo.domain.model.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book buildBook() {
        return new Book(
                BookId.generate(),
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
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
                        LocalDate.of(2008, Month.AUGUST, 1),
                        book.getPublicationDate()
                )
        );
    }

    @ParameterizedTest(name = "[{index}] title=\"{0}\", author=\"{1}\", pageCount={2}")
    @MethodSource("invalidBookArguments")
    void shouldThrowExceptionWhenFieldIsInvalid(String title, String author, int pageCount) {
        BookId bookId = BookId.generate();
        ISBN isbn = new ISBN("9780132350884");
        LocalDate publicationDate = LocalDate.now().minusYears(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(
                        bookId,
                        title,
                        isbn,
                        author,
                        pageCount,
                        publicationDate
                )
        );
    }

    private static Stream<Arguments> invalidBookArguments() {
        return Stream.of(
                Arguments.of(null, "Robert C. Martin", 464),      // title null
                Arguments.of("", "Robert C. Martin", 464),        // title blank
                Arguments.of("Clean Code", null, 464),            // author null
                Arguments.of("Clean Code", "", 464),               // author blank
                Arguments.of("Clean Code", "Robert C. Martin", 0)  // pageCount zero
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