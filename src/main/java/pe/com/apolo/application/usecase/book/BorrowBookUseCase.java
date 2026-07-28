package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

public interface BorrowBookUseCase {
    Loan execute(UserId userId, BookId bookId);
}
