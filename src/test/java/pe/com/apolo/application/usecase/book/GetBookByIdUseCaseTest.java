package pe.com.apolo.application.usecase.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.domain.repository.book.BookRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBookByIdUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private GetBookByIdUseCaseImpl useCase;

    @Test
    void shouldReturnBookWhenExists() {

        BookId id = BookId.generate();

        Book book = new Book(
                id,
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );

        when(bookRepository.findById(id))
                .thenReturn(Optional.of(book));

        Book result = useCase.execute(id);

        assertEquals(book, result);
        verify(bookRepository).findById(id);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void shouldThrowExceptionWhenBookDoesNotExist() {

        BookId id = BookId.generate();

        when(bookRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> useCase.execute(id)
        );

        verify(bookRepository).findById(id);
        verifyNoMoreInteractions(bookRepository);
    }
}