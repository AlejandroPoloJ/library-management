package pe.com.apolo.application.usecase.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.domain.repository.book.BookRepository;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBookUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CreateBookUseCaseImpl createBookUseCase;

    @Test
    void shouldCreateBook() {

        Book book = new Book(
                BookId.generate(),
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, 8, 1)
        );

        when(bookRepository.save(book))
                .thenReturn(book);
        createBookUseCase.execute(book);
        verify(bookRepository).save(book);
        verifyNoMoreInteractions(bookRepository);
    }
}