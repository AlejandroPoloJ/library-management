package pe.com.apolo.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;
import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.BookCopyStatus;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.infrastructure.persistence.entity.BookCopyEntity;
import pe.com.apolo.infrastructure.persistence.entity.BookEntity;
import pe.com.apolo.infrastructure.persistence.mapper.BookCopyMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataBookCopyRepository;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataBookRepository;

import java.util.Optional;

@Repository
public class BookCopyRepositoryAdapter implements BookCopyRepository {

    private final SpringDataBookCopyRepository repository;
    private final SpringDataBookRepository bookRepository;
    private final BookCopyMapper mapper;

    public BookCopyRepositoryAdapter(SpringDataBookCopyRepository repository,
                                     SpringDataBookRepository bookRepository,
                                     BookCopyMapper mapper) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<BookCopy> findById(BookCopyId id) {
        return repository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<BookCopy> findAvailableByBookId(BookId bookId) {
        return repository
                .findFirstByBookIdAndStatus(
                        bookId.getValue(),
                        BookCopyStatus.AVAILABLE
                )
                .map(mapper::toDomain);
    }

    @Override
    public BookCopy save(BookCopy bookCopy) {
        BookCopyEntity entity = repository.findById(bookCopy.getId().getValue())
                .orElseThrow();
        entity.setStatus(bookCopy.getStatus());
        BookCopyEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public BookCopy create(BookId bookId) {

        BookEntity book = bookRepository.findById(bookId.getValue())
                .orElseThrow(BookNotFoundException::new);

        BookCopyEntity entity = new BookCopyEntity();

        entity.setId(BookCopyId.generate().getValue());
        entity.setStatus(BookCopyStatus.AVAILABLE);
        entity.setBook(book);

        BookCopyEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

}
