package pe.com.apolo.infrastructure.persistence.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import pe.com.apolo.infrastructure.persistence.entity.BookCopyEntity;
import pe.com.apolo.infrastructure.persistence.entity.FineEntity;
import pe.com.apolo.infrastructure.persistence.entity.LoanEntity;
import pe.com.apolo.infrastructure.persistence.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FineMapperTest {

    @Autowired
    private FineMapper mapper;

    @Test
    void shouldMapEntityToDomain() {

        UUID fineId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID copyId = UUID.randomUUID();

        UserEntity user = new UserEntity(
                userId,
                "Alejandro",
                LocalDate.of(1998, Month.JANUARY, 15),
                true,
                Role.USER,
                "alejandro@test.com",
                "password"
        );

        BookCopyEntity copy = new BookCopyEntity();
        copy.setId(copyId);
        copy.setStatus(BookCopyStatus.AVAILABLE);

        LoanEntity loan = new LoanEntity();
        loan.setId(loanId);
        loan.setUser(user);
        loan.setBookCopy(copy);
        loan.setLoanDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusDays(15));
        loan.setStatus(LoanStatus.RETURNED);

        FineEntity entity = new FineEntity();
        entity.setId(fineId);
        entity.setAmount(BigDecimal.valueOf(20));
        entity.setCurrency("PEN");
        entity.setLoan(loan);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setPaidAt(null);
        entity.setStatus(FineStatus.PENDING);

        Fine fine = mapper.toDomain(entity);

        assertAll(
                () -> assertEquals(fineId, fine.getId().getValue()),
                () -> assertEquals(BigDecimal.valueOf(20), fine.getMoney().getAmount()),
                () -> assertEquals("PEN", fine.getMoney().getCurrency().getCurrencyCode()),
                () -> assertEquals(FineStatus.PENDING, fine.getStatus()),
                () -> assertEquals(loanId, fine.getLoan().getId().getValue())
        );
    }

    @Test
    void shouldMapDomainToEntity() {

        UUID fineId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID copyId = UUID.randomUUID();

        User user = new User(
                new UserId(userId),
                "Alejandro",
                LocalDate.of(1998, Month.JANUARY, 15),
                true,
                Role.USER,
                "alejandro@test.com",
                "password"
        );

        BookCopy copy = new BookCopy(
                new BookCopyId(copyId),
                BookCopyStatus.AVAILABLE
        );

        Loan loan = new Loan(
                new LoanId(loanId),
                user,
                copy,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15),
                LocalDateTime.now(),
                LoanStatus.RETURNED
        );

        Fine fine = new Fine(
                new FineId(fineId),
                new Money(
                        BigDecimal.valueOf(20),
                        Currency.getInstance("PEN")
                ),
                loan,
                LocalDateTime.now(),
                null,
                FineStatus.PENDING
        );

        FineEntity entity = mapper.toEntity(fine);

        assertAll(
                () -> assertEquals(fineId, entity.getId()),
                () -> assertEquals(BigDecimal.valueOf(20), entity.getAmount()),
                () -> assertEquals("PEN", entity.getCurrency()),
                () -> assertEquals(FineStatus.PENDING, entity.getStatus()),
                () -> assertEquals(loanId, entity.getLoan().getId())
        );
    }
}