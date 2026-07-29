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
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllBooksUseCaseTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private GetAllBooksUseCaseImpl useCase;

    @Test
    void shouldReturnAllBooks() {

        List<Book> books = List.of(
                new Book(
                        BookId.generate(),
                        "Clean Code",
                        new ISBN("9780132350884"),
                        "Robert C. Martin",
                        464,
                        LocalDate.of(2008, Month.AUGUST, 1)
                ),
                new Book(
                        BookId.generate(),
                        "Effective Java",
                        new ISBN("9780134685991"),
                        "Joshua Bloch",
                        416,
                        LocalDate.of(2018, Month.AUGUST, 6)
                )
        );

        when(bookRepository.findAll())
                .thenReturn(books);

        List<Book> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals(books, result);

        verify(bookRepository).findAll();
        verifyNoMoreInteractions(bookRepository);
    }
}