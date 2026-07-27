package pe.com.apolo.application.usecase;

import pe.com.apolo.domain.model.loan.valueobjects.LoanId;

public interface ReturnBookUseCase {

    void execute(LoanId loanId);

}