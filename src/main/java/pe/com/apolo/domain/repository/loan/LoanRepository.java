package pe.com.apolo.domain.repository.loan;

import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {

    Optional<Loan> findById(LoanId loanId);
    List<Loan> findByUserId(UserId userId);
    Loan save(Loan loan);
    int countActiveLoansByUserId(UserId userId);
}