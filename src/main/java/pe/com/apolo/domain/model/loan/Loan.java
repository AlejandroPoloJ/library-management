package pe.com.apolo.domain.model.loan;

import pe.com.apolo.domain.exception.LoanAlreadyReturnedException;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.fine.valueobjects.Money;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.User;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

public class Loan {
    private final LoanId id;
    private final User user;
    private final BookCopy bookCopy;
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;
    private LoanStatus status;
    private static final BigDecimal DAILY_FINE =
            BigDecimal.valueOf(2);

    public Loan(LoanId id, User user, BookCopy bookCopy, LocalDateTime loanDate,
            LocalDateTime dueDate, LocalDateTime returnedAt, LoanStatus status) {
        validateId(id);
        validateUser(user);
        validateBookCopy(bookCopy);
        validateLoanDate(loanDate);
        validateDueDate(loanDate, dueDate);

        this.id = id;
        this.user = user;
        this.bookCopy = bookCopy;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnedAt = returnedAt;
        this.status = status;
    }

    private static void validateId(LoanId id) {
        if (id == null) {
            throw new IllegalArgumentException("LoanId cannot be null");
        }
    }

    private static void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }

    private static void validateBookCopy(BookCopy bookCopy) {
        if (bookCopy == null) {
            throw new IllegalArgumentException("BookCopy cannot be null");
        }
    }

    private static void validateLoanDate(LocalDateTime loanDate) {
        if (loanDate == null) {
            throw new IllegalArgumentException("LoanDate cannot be null");
        }
    }

    private static void validateDueDate(LocalDateTime loanDate,
                                        LocalDateTime dueDate) {

        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }

        if (!dueDate.isAfter(loanDate)) {
            throw new IllegalArgumentException("Due date must be after loan date.");
        }
    }

    public void returnBook() {
        if (status != LoanStatus.ACTIVE) {
            throw new LoanAlreadyReturnedException();
        }

        bookCopy.returnBook();

        this.returnedAt = LocalDateTime.now();
        this.status = LoanStatus.RETURNED;
    }

    public boolean isOverdue() {

        LocalDateTime referenceDate =
                returnedAt == null
                        ? LocalDateTime.now()
                        : returnedAt;

        return referenceDate.isAfter(dueDate);
    }

    public long getOverdueDays() {

        if (!isOverdue()) {
            return 0;
        }

        LocalDateTime referenceDate =
                returnedAt == null
                        ? LocalDateTime.now()
                        : returnedAt;

        return Duration.between(dueDate, referenceDate).toDays();
    }

    private Money calculateFine() {
        BigDecimal amount = DAILY_FINE.multiply(
                BigDecimal.valueOf(getOverdueDays())
        );

        return new Money(amount, Currency.getInstance("PEN"));
    }

    public Optional<Fine> generateFine() {
        if (!isOverdue()) {
            return Optional.empty();
        }

        Money amount = calculateFine();

        return Optional.of(Fine.create(amount, this));
    }

    public static Loan create(User user, BookCopy copy) {
        LocalDateTime loanDate = LocalDateTime.now();
        return new Loan(
                LoanId.generate(),
                user,
                copy,
                loanDate,
                loanDate.plusDays(15),
                null,
                LoanStatus.ACTIVE
        );
    }

    public void forceLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(15);
    }

    public boolean isActive() {
        return status == LoanStatus.ACTIVE;
    }

    public LoanId getId() {
        return id;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public User getUser() {
        return user;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }
}
