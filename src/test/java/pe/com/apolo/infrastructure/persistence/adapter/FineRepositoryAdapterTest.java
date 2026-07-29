package pe.com.apolo.infrastructure.persistence.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import pe.com.apolo.infrastructure.persistence.entity.FineEntity;
import pe.com.apolo.infrastructure.persistence.mapper.FineMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataFineRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FineRepositoryAdapterTest {

    @Mock
    private SpringDataFineRepository repository;

    @Mock
    private FineMapper mapper;

    @InjectMocks
    private FineRepositoryAdapter adapter;

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

    private Fine buildFine(UUID fineId, Loan loan) {
        Money money = new Money(BigDecimal.TEN, Currency.getInstance("PEN"));
        return new Fine(
                new FineId(fineId),
                money,
                loan,
                LocalDateTime.of(2026, Month.JANUARY, 20, 10, 0),
                null,
                FineStatus.PENDING
        );
    }

    private FineEntity buildEntity(UUID id) {
        FineEntity entity = new FineEntity();
        entity.setId(id);
        entity.setAmount(BigDecimal.TEN);
        entity.setCurrency("PEN");
        entity.setCreatedAt(LocalDateTime.of(2026, Month.JANUARY, 20, 10, 0));
        entity.setStatus(FineStatus.PENDING);
        return entity;
    }

    @Test
    void shouldSaveFine() {
        UUID userId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        UUID fineId = UUID.randomUUID();

        User user = buildUser(userId);
        Loan loan = buildLoan(loanId, user);
        Fine fine = buildFine(fineId, loan);
        FineEntity entity = buildEntity(fineId);
        FineEntity savedEntity = buildEntity(fineId);
        Fine savedFine = buildFine(fineId, loan);

        when(mapper.toEntity(fine)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedFine);

        Fine result = adapter.save(fine);

        assertThat(result).isEqualTo(savedFine);
        verify(repository).save(entity);
    }

    @Test
    void shouldReturnTrueWhenUserHasPendingFine() {
        UUID userId = UUID.randomUUID();

        when(repository.existsByLoanUserIdAndStatus(userId, FineStatus.PENDING))
                .thenReturn(true);

        boolean result = adapter.existsPendingByUserId(new UserId(userId));

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenUserHasNoPendingFine() {
        UUID userId = UUID.randomUUID();

        when(repository.existsByLoanUserIdAndStatus(userId, FineStatus.PENDING))
                .thenReturn(false);

        boolean result = adapter.existsPendingByUserId(new UserId(userId));

        assertThat(result).isFalse();
    }

    @Test
    void shouldFindFineById() {
        UUID userId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        UUID fineId = UUID.randomUUID();

        User user = buildUser(userId);
        Loan loan = buildLoan(loanId, user);
        Fine fine = buildFine(fineId, loan);
        FineEntity entity = buildEntity(fineId);

        when(repository.findById(fineId)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(fine);

        Optional<Fine> result = adapter.findById(new FineId(fineId));

        assertThat(result).contains(fine);
    }

    @Test
    void shouldReturnEmptyWhenFineNotFoundById() {
        UUID fineId = UUID.randomUUID();

        when(repository.findById(fineId)).thenReturn(Optional.empty());

        Optional<Fine> result = adapter.findById(new FineId(fineId));

        assertThat(result).isEmpty();
        verify(mapper, never()).toDomain(any(FineEntity.class));
    }

    @Test
    void shouldFindFinesByUserId() {
        UUID userId = UUID.randomUUID();
        UUID loanId = UUID.randomUUID();
        UUID fineId = UUID.randomUUID();

        User user = buildUser(userId);
        Loan loan = buildLoan(loanId, user);
        Fine fine = buildFine(fineId, loan);
        FineEntity entity = buildEntity(fineId);

        when(repository.findByLoanUserId(userId)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(fine);

        List<Fine> result = adapter.findByUserId(new UserId(userId));

        assertThat(result).containsExactly(fine);
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoFines() {
        UUID userId = UUID.randomUUID();

        when(repository.findByLoanUserId(userId)).thenReturn(List.of());

        List<Fine> result = adapter.findByUserId(new UserId(userId));

        assertThat(result).isEmpty();
    }
}