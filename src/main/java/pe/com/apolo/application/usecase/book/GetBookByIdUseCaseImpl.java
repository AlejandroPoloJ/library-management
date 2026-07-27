package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.repository.book.BookRepository;

public class GetBookByIdUseCaseImpl implements GetBookByIdUseCase {

    private final BookRepository bookRepository;

    public GetBookByIdUseCaseImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book execute(BookId id) {

        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }
}