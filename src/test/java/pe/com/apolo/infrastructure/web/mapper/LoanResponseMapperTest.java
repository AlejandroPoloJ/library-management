package pe.com.apolo.infrastructure.web.mapper;

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
import pe.com.apolo.infrastructure.web.dto.response.LoanResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanResponseMapperTest {

    @Autowired
    private LoanResponseMapper mapper;

    @Test
    void shouldMapLoanToResponse() {

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
                "123456"
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

        LoanResponse response = mapper.toResponse(loan);

        assertAll(
                () -> assertEquals(loanId, response.loanId()),
                () -> assertEquals(userId, response.userId()),
                () -> assertEquals(copyId, response.bookCopyId()),
                () -> assertEquals(loan.getLoanDate(), response.loanDate()),
                () -> assertEquals(loan.getDueDate(), response.dueDate()),
                () -> assertEquals("ACTIVE", response.status())
        );
    }
}