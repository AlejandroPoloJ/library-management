package pe.com.apolo.infrastructure.persistence.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.infrastructure.persistence.entity.BookEntity;
import pe.com.apolo.infrastructure.persistence.mapper.BookMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataBookRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryAdapterTest {

    @Mock
    private SpringDataBookRepository repository;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookRepositoryAdapter adapter;

    private Book buildBook(UUID id) {
        return new Book(
                new BookId(id),
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );
    }

    private BookEntity buildEntity(UUID id) {
        return new BookEntity(
                id,
                "Clean Code",
                "9780132350884",
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );
    }

    @Test
    void shouldFindBookById() {
        UUID id = UUID.randomUUID();
        BookEntity entity = buildEntity(id);
        Book domain = buildBook(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<Book> result = adapter.findById(new BookId(id));

        assertThat(result).contains(domain);
    }

    @Test
    void shouldReturnEmptyWhenBookNotFoundById() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> result = adapter.findById(new BookId(id));

        assertThat(result).isEmpty();
        verify(mapper, never()).toDomain(any(BookEntity.class));
    }

    @Test
    void shouldFindAllBooks() {
        UUID id = UUID.randomUUID();
        BookEntity entity = buildEntity(id);
        Book domain = buildBook(id);

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<Book> result = adapter.findAll();

        assertThat(result).containsExactly(domain);
    }

    @Test
    void shouldReturnEmptyListWhenNoBooksExist() {
        when(repository.findAll()).thenReturn(List.of());

        List<Book> result = adapter.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveBook() {
        UUID id = UUID.randomUUID();
        Book domain = buildBook(id);
        BookEntity entity = buildEntity(id);
        BookEntity savedEntity = buildEntity(id);
        Book savedDomain = buildBook(id);

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedDomain);

        Book result = adapter.save(domain);

        assertThat(result).isEqualTo(savedDomain);
        verify(repository).save(entity);
    }
}