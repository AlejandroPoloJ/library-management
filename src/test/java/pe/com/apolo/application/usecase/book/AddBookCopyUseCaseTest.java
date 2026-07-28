package pe.com.apolo.application.usecase.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.domain.repository.book.BookRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddBookCopyUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookCopyRepository bookCopyRepository;

    @InjectMocks
    private AddBookCopyUseCaseImpl useCase;

    @Test
    void shouldAddBookCopy() {

        BookId bookId = BookId.generate();

        Book book = new Book(
                bookId,
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, 8, 1)
        );

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));

        when(bookCopyRepository.create(bookId))
                .thenReturn(BookCopy.create());

        useCase.execute(bookId);

        verify(bookRepository).findById(bookId);
        verify(bookCopyRepository).create(bookId);
        verifyNoMoreInteractions(bookRepository, bookCopyRepository);
    }

    @Test
    void shouldThrowExceptionWhenBookDoesNotExist() {

        BookId bookId = BookId.generate();

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> useCase.execute(bookId)
        );

        verify(bookRepository).findById(bookId);
        verify(bookCopyRepository, never()).create(any());
        verifyNoMoreInteractions(bookRepository, bookCopyRepository);
    }
}