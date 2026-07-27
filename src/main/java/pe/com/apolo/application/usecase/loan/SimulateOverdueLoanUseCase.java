package pe.com.apolo.application.usecase.loan;

import pe.com.apolo.domain.model.loan.valueobjects.LoanId;

public interface SimulateOverdueLoanUseCase {

    void execute(LoanId loanId, int overdueDays);

}
