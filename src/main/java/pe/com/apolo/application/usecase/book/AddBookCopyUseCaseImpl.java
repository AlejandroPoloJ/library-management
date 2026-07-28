package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.domain.repository.book.BookRepository;

public class AddBookCopyUseCaseImpl implements AddBookCopyUseCase {

    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    public AddBookCopyUseCaseImpl(
            BookRepository bookRepository,
            BookCopyRepository bookCopyRepository
    ) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public void execute(BookId bookId) {

        bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        bookCopyRepository.create(bookId);
    }
}