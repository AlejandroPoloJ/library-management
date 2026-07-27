package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.repository.book.BookRepository;

import java.util.List;

public class GetAllBooksUseCaseImpl implements GetAllBooksUseCase {

    private final BookRepository bookRepository;

    public GetAllBooksUseCaseImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> execute() {
        return bookRepository.findAll();
    }
}