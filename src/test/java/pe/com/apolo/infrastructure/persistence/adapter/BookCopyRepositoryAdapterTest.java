package pe.com.apolo.infrastructure.persistence.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.BookCopyStatus;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.infrastructure.persistence.entity.BookCopyEntity;
import pe.com.apolo.infrastructure.persistence.entity.BookEntity;
import pe.com.apolo.infrastructure.persistence.mapper.BookCopyMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataBookCopyRepository;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataBookRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCopyRepositoryAdapterTest {

    @Mock
    private SpringDataBookCopyRepository repository;

    @Mock
    private SpringDataBookRepository bookRepository;

    @Mock
    private BookCopyMapper mapper;

    @InjectMocks
    private BookCopyRepositoryAdapter adapter;

    private BookCopyEntity buildEntity(UUID id, BookCopyStatus status) {
        BookCopyEntity entity = new BookCopyEntity();
        entity.setId(id);
        entity.setStatus(status);
        return entity;
    }

    @Test
    void shouldFindBookCopyById() {
        UUID id = UUID.randomUUID();
        BookCopyEntity entity = buildEntity(id, BookCopyStatus.AVAILABLE);
        BookCopy domain = new BookCopy(new BookCopyId(id), BookCopyStatus.AVAILABLE);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<BookCopy> result = adapter.findById(new BookCopyId(id));

        assertThat(result).contains(domain);
    }

    @Test
    void shouldReturnEmptyWhenBookCopyNotFoundById() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<BookCopy> result = adapter.findById(new BookCopyId(id));

        assertThat(result).isEmpty();
        verify(mapper, never()).toDomain(any(BookCopyEntity.class));
    }

    @Test
    void shouldFindAvailableCopyByBookId() {
        UUID bookId = UUID.randomUUID();
        UUID copyId = UUID.randomUUID();
        BookCopyEntity entity = buildEntity(copyId, BookCopyStatus.AVAILABLE);
        BookCopy domain = new BookCopy(new BookCopyId(copyId), BookCopyStatus.AVAILABLE);

        when(repository.findFirstByBookIdAndStatus(bookId, BookCopyStatus.AVAILABLE))
                .thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<BookCopy> result = adapter.findAvailableByBookId(new BookId(bookId));

        assertThat(result).contains(domain);
    }

    @Test
    void shouldReturnEmptyWhenNoAvailableCopyForBookId() {
        UUID bookId = UUID.randomUUID();

        when(repository.findFirstByBookIdAndStatus(bookId, BookCopyStatus.AVAILABLE))
                .thenReturn(Optional.empty());

        Optional<BookCopy> result = adapter.findAvailableByBookId(new BookId(bookId));

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveBookCopyUpdatingStatus() {
        UUID id = UUID.randomUUID();
        BookCopyEntity existingEntity = buildEntity(id, BookCopyStatus.AVAILABLE);
        BookCopyEntity savedEntity = buildEntity(id, BookCopyStatus.LOANED);
        BookCopy domain = new BookCopy(new BookCopyId(id), BookCopyStatus.LOANED);
        BookCopy resultDomain = new BookCopy(new BookCopyId(id), BookCopyStatus.LOANED);

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.save(existingEntity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(resultDomain);

        BookCopy result = adapter.save(domain);

        assertThat(result).isEqualTo(resultDomain);
        assertThat(existingEntity.getStatus()).isEqualTo(BookCopyStatus.LOANED);
        verify(repository).save(existingEntity);
    }

    @Test
    void shouldThrowWhenSavingNonExistentBookCopy() {
        UUID id = UUID.randomUUID();
        BookCopy domain = new BookCopy(new BookCopyId(id), BookCopyStatus.LOANED);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.save(domain))
                .isInstanceOf(java.util.NoSuchElementException.class);
    }

    @Test
    void shouldCreateBookCopyWhenBookExists() {
        UUID bookId = UUID.randomUUID();
        BookEntity book = new BookEntity();
        book.setId(bookId);

        BookCopy resultDomain = BookCopy.create();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(repository.save(any(BookCopyEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDomain(any(BookCopyEntity.class))).thenReturn(resultDomain);

        BookCopy result = adapter.create(new BookId(bookId));

        assertThat(result).isEqualTo(resultDomain);

        verify(repository).save(argThat(entity ->
                entity.getStatus() == BookCopyStatus.AVAILABLE
                        && entity.getBook() == book
                        && entity.getId() != null
        ));
    }

    @Test
    void shouldThrowBookNotFoundWhenCreatingCopyForNonExistentBook() {
        UUID bookId = UUID.randomUUID();
        BookId id = new BookId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.create(id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found");

        verify(repository, never()).save(any());
    }
}