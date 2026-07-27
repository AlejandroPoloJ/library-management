package pe.com.apolo.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.repository.book.BookRepository;
import pe.com.apolo.infrastructure.persistence.mapper.BookMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataBookRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryAdapter implements BookRepository {

    private final SpringDataBookRepository repository;
    private final BookMapper mapper;

    public BookRepositoryAdapter(
            SpringDataBookRepository repository,
            BookMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Book> findById(BookId id) {
        return repository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Book save(Book book) {
        return mapper.toDomain(
                repository.save(
                        mapper.toEntity(book)
                )
        );
    }
}