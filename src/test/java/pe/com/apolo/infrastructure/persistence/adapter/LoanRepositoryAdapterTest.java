package pe.com.apolo.infrastructure.persistence.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.BookCopyStatus;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.LoanStatus;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.persistence.entity.LoanEntity;
import pe.com.apolo.infrastructure.persistence.mapper.LoanMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataLoanRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanRepositoryAdapterTest {

    @Mock
    private SpringDataLoanRepository repository;

    @Mock
    private LoanMapper mapper;

    @InjectMocks
    private LoanRepositoryAdapter adapter;

    private User buildUser(UUID id) {
        return new User(
                new UserId(id),
                "Juan Pérez",
                LocalDate.of(1990, Month.JANUARY, 1),
                true,
                Role.USER,
                "juan@apolo.com",
                "123456"
        );
    }

    private Loan buildLoan(UUID loanId, User user) {
        BookCopy bookCopy = new BookCopy(BookCopyId.generate(), BookCopyStatus.LOANED);
        LocalDateTime loanDate = LocalDateTime.of(2026, Month.JANUARY, 1, 10, 0);

        return new Loan(
                new LoanId(loanId),
                user,
                bookCopy,
                loanDate,
                loanDate.plusDays(15),
                null,
                LoanStatus.ACTIVE
        );
    }

    private LoanEntity buildEntity(UUID id) {
        LoanEntity entity = new LoanEntity();
        entity.setId(id);
        entity.setLoanDate(LocalDateTime.of(2026, Month.JANUARY, 1, 10, 0));
        entity.setDueDate(LocalDateTime.of(2026, Month.JANUARY, 16, 10, 0));
        entity.setStatus(LoanStatus.ACTIVE);
        return entity;
    }

    @Test
    void shouldFindLoanById() {
        UUID userId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        User user = buildUser(userId);
        Loan loan = buildLoan(loanId, user);
        LoanEntity entity = buildEntity(loanId);

        when(repository.findById(loanId)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(loan);

        Optional<Loan> result = adapter.findById(new LoanId(loanId));

        assertThat(result).contains(loan);
    }

    @Test
    void shouldReturnEmptyWhenLoanNotFoundById() {
        UUID loanId = UUID.randomUUID();

        when(repository.findById(loanId)).thenReturn(Optional.empty());

        Optional<Loan> result = adapter.findById(new LoanId(loanId));

        assertThat(result).isEmpty();
        verify(mapper, never()).toDomain(any(LoanEntity.class));
    }

    @Test
    void shouldFindLoansByUserId() {
        UUID userId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        User user = buildUser(userId);
        Loan loan = buildLoan(loanId, user);
        LoanEntity entity = buildEntity(loanId);

        when(repository.findByUserId(userId)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(loan);

        List<Loan> result = adapter.findByUserId(new UserId(userId));

        assertThat(result).containsExactly(loan);
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoLoans() {
        UUID userId = UUID.randomUUID();

        when(repository.findByUserId(userId)).thenReturn(List.of());

        List<Loan> result = adapter.findByUserId(new UserId(userId));

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveLoan() {
        UUID userId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();

        User user = buildUser(userId);
        Loan loan = buildLoan(loanId, user);
        LoanEntity entity = buildEntity(loanId);
        LoanEntity savedEntity = buildEntity(loanId);
        Loan savedLoan = buildLoan(loanId, user);

        when(mapper.toEntity(loan)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedLoan);

        Loan result = adapter.save(loan);

        assertThat(result).isEqualTo(savedLoan);
        verify(repository).save(entity);
    }

    @Test
    void shouldCountActiveLoansByUserId() {
        UUID userId = UUID.randomUUID();

        when(repository.countByUserIdAndStatus(userId, LoanStatus.ACTIVE))
                .thenReturn(3L);

        int result = adapter.countActiveLoansByUserId(new UserId(userId));

        assertThat(result).isEqualTo(3);
    }

    @Test
    void shouldReturnZeroWhenUserHasNoActiveLoans() {
        UUID userId = UUID.randomUUID();

        when(repository.countByUserIdAndStatus(userId, LoanStatus.ACTIVE))
                .thenReturn(0L);

        int result = adapter.countActiveLoansByUserId(new UserId(userId));

        assertThat(result).isZero();
    }
}