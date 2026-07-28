package pe.com.apolo.application.usecase.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.LoanNotFoundException;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.domain.repository.fine.FineRepository;
import pe.com.apolo.domain.repository.loan.LoanRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReturnBookUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookCopyRepository bookCopyRepository;

    @Mock
    private FineRepository fineRepository;

    @InjectMocks
    private ReturnBookUseCaseImpl useCase;

    private Loan buildLoan() {

        User user = new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.now().minusYears(25),
                true,
                Role.USER,
                "test@test.com",
                "123456"
        );

        BookCopy copy = BookCopy.create();
        copy.loan();

        return new Loan(
                LoanId.generate(),
                user,
                copy,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15),
                null,
                pe.com.apolo.domain.model.loan.LoanStatus.ACTIVE
        );
    }

    @Test
    void shouldReturnBookWithoutFine() {

        Loan loan = buildLoan();

        when(loanRepository.findById(loan.getId()))
                .thenReturn(Optional.of(loan));

        useCase.execute(loan.getId());

        verify(loanRepository).findById(loan.getId());
        verify(bookCopyRepository).save(loan.getBookCopy());
        verify(loanRepository).save(loan);
        verify(fineRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenLoanDoesNotExist() {

        LoanId id = LoanId.generate();

        when(loanRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                LoanNotFoundException.class,
                () -> useCase.execute(id)
        );

        verify(loanRepository).findById(id);
        verifyNoInteractions(bookCopyRepository, fineRepository);
    }

    @Test
    void shouldGenerateFineWhenLoanIsOverdue() {

        Loan loan = buildLoan();

        loan.forceLoanDate(
                LocalDateTime.now().minusDays(20)
        );

        when(loanRepository.findById(loan.getId()))
                .thenReturn(Optional.of(loan));

        useCase.execute(loan.getId());

        verify(fineRepository).save(any());
        verify(bookCopyRepository).save(loan.getBookCopy());
        verify(loanRepository).save(loan);
    }
}