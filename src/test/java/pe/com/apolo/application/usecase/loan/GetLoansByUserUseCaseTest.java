package pe.com.apolo.application.usecase.loan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.loan.LoanRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLoansByUserUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private GetLoansByUserUseCaseImpl useCase;

    @Test
    void shouldReturnLoansByUser() {

        UserId userId = UserId.generate();

        List<Loan> loans = List.of(
                mock(Loan.class),
                mock(Loan.class)
        );

        when(loanRepository.findByUserId(userId))
                .thenReturn(loans);

        List<Loan> result = useCase.execute(userId);

        assertEquals(loans, result);

        verify(loanRepository).findByUserId(userId);
        verifyNoMoreInteractions(loanRepository);
    }
}