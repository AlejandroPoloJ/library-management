package pe.com.apolo.application.usecase.loan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.LoanNotFoundException;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.repository.loan.LoanRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulateOverdueLoanUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private SimulateOverdueLoanUseCaseImpl useCase;

    @Test
    void shouldSimulateOverdueLoan() {

        Loan loan = mock(Loan.class);
        LoanId loanId = LoanId.generate();

        when(loanRepository.findById(loanId))
                .thenReturn(Optional.of(loan));

        useCase.execute(loanId, 7);

        verify(loanRepository).findById(loanId);
        verify(loan).forceLoanDate(any(LocalDateTime.class));
        verify(loanRepository).save(loan);
        verifyNoMoreInteractions(loanRepository, loan);
    }

    @Test
    void shouldThrowExceptionWhenLoanDoesNotExist() {

        LoanId loanId = LoanId.generate();

        when(loanRepository.findById(loanId))
                .thenReturn(Optional.empty());

        assertThrows(
                LoanNotFoundException.class,
                () -> useCase.execute(loanId, 7)
        );

        verify(loanRepository).findById(loanId);
        verify(loanRepository, never()).save(any());
        verifyNoMoreInteractions(loanRepository);
    }
}