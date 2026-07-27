package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.repository.book.BookRepository;

public class CreateBookUseCaseImpl implements CreateBookUseCase {

    private final BookRepository bookRepository;

    public CreateBookUseCaseImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(Book book) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }

        bookRepository.save(book);
    }
}