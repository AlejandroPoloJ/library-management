package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.book.valueobjects.BookId;

public interface AddBookCopyUseCase {

    void execute(BookId bookId);

}