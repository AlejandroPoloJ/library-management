package pe.com.apolo.infrastructure.web.mapper;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.BookCopyStatus;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.fine.FineStatus;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.model.fine.valueobjects.Money;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.LoanStatus;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.web.dto.response.FineResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FineResponseMapperTest {

    private final FineResponseMapper mapper = new FineResponseMapper();

    @Test
    void shouldMapFineToResponse() {
        UUID userId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        UUID fineId = UUID.randomUUID();

        User user = new User(
                new UserId(userId), "Juan Pérez", LocalDate.of(1990, Month.JANUARY, 1),
                true, Role.USER, "juan@apolo.com", "123456"
        );

        BookCopy bookCopy = new BookCopy(BookCopyId.generate(), BookCopyStatus.LOANED);
        LocalDateTime loanDate = LocalDateTime.of(2026, Month.JANUARY, 1, 10, 0);

        Loan loan = new Loan(
                new LoanId(loanId), user, bookCopy, loanDate,
                loanDate.plusDays(15), null, LoanStatus.ACTIVE
        );

        Money money = new Money(BigDecimal.TEN, Currency.getInstance("PEN"));
        LocalDateTime createdAt = LocalDateTime.of(2026, Month.JANUARY, 20, 10, 0);

        Fine fine = new Fine(
                new FineId(fineId), money, loan, createdAt, null, FineStatus.PENDING
        );

        FineResponse response = mapper.toResponse(fine);

        assertThat(response.id()).isEqualTo(fineId);
        assertThat(response.amount()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(response.currency()).isEqualTo("PEN");
        assertThat(response.status()).isEqualTo(FineStatus.PENDING);
        assertThat(response.createdAt()).isEqualTo(createdAt);
        assertThat(response.paidAt()).isNull();
        assertThat(response.loanId()).isEqualTo(loanId);
    }
}