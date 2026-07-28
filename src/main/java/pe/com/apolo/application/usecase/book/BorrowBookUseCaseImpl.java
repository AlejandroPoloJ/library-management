package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.exception.BookNotAvailableException;
import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.exception.UserNotFoundException;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.domain.repository.book.BookRepository;
import pe.com.apolo.domain.repository.fine.FineRepository;
import pe.com.apolo.domain.repository.loan.LoanRepository;
import pe.com.apolo.domain.repository.user.UserRepository;

public class BorrowBookUseCaseImpl implements BorrowBookUseCase {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    public BorrowBookUseCaseImpl(
            UserRepository userRepository,
            BookRepository bookRepository, BookCopyRepository bookCopyRepository,
            LoanRepository loanRepository,
            FineRepository fineRepository
    ) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
    }

    @Override
    public Loan execute(UserId userId, BookId bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        long activeLoans = loanRepository.countActiveLoansByUserId(userId);

        boolean hasPendingFines =
                fineRepository.existsPendingByUserId(userId);

        user.validateCanBorrowBook(
                (int) activeLoans,
                hasPendingFines
        );

        bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        BookCopy copy = bookCopyRepository
                .findAvailableByBookId(bookId)
                .orElseThrow(BookNotAvailableException::new);

        Loan loan = Loan.create(user, copy);

        bookCopyRepository.save(copy);
        loanRepository.save(loan);

        return loan;
    }
}
