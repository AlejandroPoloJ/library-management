package pe.com.apolo.domain.model.book;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.exception.BookAlreadyAvailableException;
import pe.com.apolo.domain.exception.BookNotAvailableException;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;

import static org.junit.jupiter.api.Assertions.*;

class BookCopyTest {

    @Test
    void shouldCreateAvailableBookCopy() {

        BookCopy copy = BookCopy.create();
        assertTrue(copy.isAvailable());
    }

    @Test
    void shouldLoanAvailableBook() {

        BookCopy copy = BookCopy.create();
        copy.loan();
        assertFalse(copy.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenLoaningUnavailableBook() {

        BookCopy copy = BookCopy.create();
        copy.loan();
        assertThrows(
                BookNotAvailableException.class,
                copy::loan
        );
    }

    @Test
    void shouldReturnLoanedBook() {

        BookCopy copy = BookCopy.create();
        copy.loan();
        copy.returnBook();
        assertTrue(copy.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenReturningAvailableBook() {

        BookCopy copy = BookCopy.create();
        assertThrows(
                BookAlreadyAvailableException.class,
                copy::returnBook
        );
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BookCopy(null, BookCopyStatus.AVAILABLE)
        );
    }

    @Test
    void shouldInitializeAsAvailableWhenStatusIsNull() {

        BookCopy copy = new BookCopy(
                BookCopyId.generate(),
                null
        );
        assertTrue(copy.isAvailable());
    }

}