package pe.com.apolo.domain.model.fine;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.exception.FineAlreadyPaidException;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.fine.valueobjects.Money;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class FineTest {

    private Loan buildLoan() {

        User user = new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.now().minusYears(25),
                true,
                Role.USER,
                "alejandro@test.com",
                "123456"
        );

        return Loan.create(
                user,
                BookCopy.create()
        );
    }

    private Money buildMoney() {
        return new Money(
                BigDecimal.TEN,
                Currency.getInstance("PEN")
        );
    }

    @Test
    void shouldCreateFineSuccessfully() {

        Fine fine = Fine.create(
                buildMoney(),
                buildLoan()
        );
        assertAll(
                () -> assertNotNull(fine.getId()),
                () -> assertEquals(
                        FineStatus.PENDING,
                        fine.getStatus()
                ),
                () -> assertNull(fine.getPaidAt()),
                () -> assertNotNull(fine.getCreatedAt()),
                () -> assertTrue(fine.isPending()
                )
        );
    }

    @Test
    void shouldPayFine() {

        Fine fine = Fine.create(
                buildMoney(),
                buildLoan()
        );
        fine.pay();
        assertAll(
                () -> assertEquals(
                        FineStatus.PAID,
                        fine.getStatus()
                ),
                () -> assertNotNull(fine.getPaidAt()),
                () -> assertFalse(fine.isPending())
        );
    }

    @Test
    void shouldThrowExceptionWhenPayingPaidFine() {

        Fine fine = Fine.create(
                buildMoney(),
                buildLoan()
        );
        fine.pay();
        assertThrows(FineAlreadyPaidException.class, fine::pay);
    }

    @Test
    void shouldCancelPendingFine() {

        Fine fine = Fine.create(
                buildMoney(),
                buildLoan()
        );
        fine.cancel();
        assertEquals(FineStatus.CANCELLED, fine.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenCancellingNonPendingFine() {

        Fine fine = Fine.create(
                buildMoney(),
                buildLoan()
        );
        fine.pay();
        assertThrows(IllegalStateException.class, fine::cancel);
    }

    @Test
    void shouldForgivePendingFine() {

        Fine fine = Fine.create(
                buildMoney(),
                buildLoan()
        );
        fine.forgive();
        assertEquals(FineStatus.FORGIVEN, fine.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenForgivingNonPendingFine() {

        Fine fine = Fine.create(
                buildMoney(),
                buildLoan()
        );
        fine.pay();
        assertThrows(IllegalStateException.class, fine::forgive);
    }

    @Test
    void shouldThrowExceptionWhenMoneyIsNull() {
        Loan loan = buildLoan();

        assertThrows(
                IllegalArgumentException.class,
                () -> Fine.create(null, loan)
        );
    }

    @Test
    void shouldThrowExceptionWhenLoanIsNull() {
        Money money = buildMoney();

        assertThrows(
                IllegalArgumentException.class,
                () -> Fine.create(money, null)
        );
    }
}