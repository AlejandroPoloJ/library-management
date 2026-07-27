package pe.com.apolo.application.usecase.loan;

import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.loan.LoanRepository;

import java.util.List;

public class GetLoansByUserUseCaseImpl implements GetLoansByUserUseCase {

    private final LoanRepository loanRepository;

    public GetLoansByUserUseCaseImpl(
            LoanRepository loanRepository
    ) {
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Loan> execute(UserId userId) {
        return loanRepository.findByUserId(userId);
    }
}