package pe.com.apolo.infrastructure.persistence.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.BookCopyStatus;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.LoanStatus;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.persistence.entity.BookCopyEntity;
import pe.com.apolo.infrastructure.persistence.entity.LoanEntity;
import pe.com.apolo.infrastructure.persistence.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanMapperTest {

    @Autowired
    private LoanMapper mapper;

    @Test
    void shouldMapEntityToDomain() {

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
        copy.setStatus(BookCopyStatus.LOANED);

        LoanEntity entity = new LoanEntity();
        entity.setId(loanId);
        entity.setUser(user);
        entity.setBookCopy(copy);
        entity.setLoanDate(LocalDateTime.now());
        entity.setDueDate(LocalDateTime.now().plusDays(15));
        entity.setReturnedAt(null);
        entity.setStatus(LoanStatus.ACTIVE);

        Loan loan = mapper.toDomain(entity);

        assertAll(
                () -> assertEquals(loanId, loan.getId().getValue()),
                () -> assertEquals(userId, loan.getUser().getId().getValue()),
                () -> assertEquals(copyId, loan.getBookCopy().getId().getValue()),
                () -> assertEquals(LoanStatus.ACTIVE, loan.getStatus())
        );
    }

    @Test
    void shouldMapDomainToEntity() {

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
                BookCopyStatus.LOANED
        );

        Loan loan = new Loan(
                new LoanId(loanId),
                user,
                copy,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15),
                null,
                LoanStatus.ACTIVE
        );

        LoanEntity entity = mapper.toEntity(loan);

        assertAll(
                () -> assertEquals(loanId, entity.getId()),
                () -> assertEquals(userId, entity.getUser().getId()),
                () -> assertEquals(copyId, entity.getBookCopy().getId()),
                () -> assertEquals(LoanStatus.ACTIVE, entity.getStatus())
        );
    }
}