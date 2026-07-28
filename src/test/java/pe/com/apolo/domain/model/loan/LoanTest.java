package pe.com.apolo.domain.model.loan;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.exception.LoanAlreadyReturnedException;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    private User buildUser() {
        return new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.now().minusYears(25),
                true,
                Role.USER,
                "alejandro@test.com",
                "123456"
        );
    }

    @Test
    void shouldCreateLoanSuccessfully() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        assertAll(
                () -> assertNotNull(loan.getId()),
                () -> assertEquals(LoanStatus.ACTIVE, loan.getStatus()),
                () -> assertNull(loan.getReturnedAt()),
                () -> assertEquals(
                        loan.getLoanDate().plusDays(15),
                        loan.getDueDate()
                )
        );
    }

    @Test
    void shouldReturnLoanSuccessfully() {

        Loan loan = Loan.create(buildUser(), BookCopy.create());
        loan.returnBook();
        assertAll(
                () -> assertEquals(
                        LoanStatus.RETURNED,
                        loan.getStatus()
                ),
                () -> assertNotNull(
                        loan.getReturnedAt()
                ),
                () -> assertTrue(
                        loan.getBookCopy().isAvailable()
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenReturningReturnedLoan() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        loan.returnBook();
        assertThrows(
                LoanAlreadyReturnedException.class,
                loan::returnBook
        );
    }

    @Test
    void shouldDetectOverdueLoan() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        loan.forceLoanDate(
                LocalDateTime.now().minusDays(20)
        );
        assertTrue(
                loan.isOverdue()
        );
    }

    @Test
    void shouldNotDetectLoanAsOverdue() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        assertFalse(loan.isOverdue());
    }

    @Test
    void shouldCalculateOverdueDays() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        loan.forceLoanDate(LocalDateTime.now().minusDays(20));
        assertEquals(5, loan.getOverdueDays());
    }

    @Test
    void shouldReturnZeroOverdueDays() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        assertEquals(0, loan.getOverdueDays());
    }

    @Test
    void shouldGenerateFineWhenLoanIsOverdue() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        loan.forceLoanDate(
                LocalDateTime.now().minusDays(20)
        );
        Optional<Fine> fine = loan.generateFine();
        assertTrue(fine.isPresent());
        assertEquals(loan, fine.get().getLoan());
    }

    @Test
    void shouldNotGenerateFineWhenLoanIsNotOverdue() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        assertTrue(loan.generateFine().isEmpty());
    }

    @Test
    void shouldForceLoanDate() {

        Loan loan = Loan.create(
                buildUser(),
                BookCopy.create()
        );
        LocalDateTime loanDate =
                LocalDateTime.now().minusDays(30);
        loan.forceLoanDate(loanDate);
        assertAll(
                () -> assertEquals(
                        loanDate,
                        loan.getLoanDate()
                ),
                () -> assertEquals(
                        loanDate.plusDays(15),
                        loan.getDueDate()
                )
        );
    }
}