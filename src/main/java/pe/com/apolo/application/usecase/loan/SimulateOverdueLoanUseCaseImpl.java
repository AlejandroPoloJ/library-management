package pe.com.apolo.application.usecase.loan;

import pe.com.apolo.domain.exception.LoanNotFoundException;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.repository.loan.LoanRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SimulateOverdueLoanUseCaseImpl implements SimulateOverdueLoanUseCase {

    private final LoanRepository loanRepository;

    public SimulateOverdueLoanUseCaseImpl(
            LoanRepository loanRepository
    ) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void execute(
            LoanId loanId,
            int overdueDays
    ) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(LoanNotFoundException::new);

        loan.forceLoanDate(
                LocalDateTime.now(ZoneId.systemDefault()).minusDays(22)
        );

        loanRepository.save(loan);
    }
}
