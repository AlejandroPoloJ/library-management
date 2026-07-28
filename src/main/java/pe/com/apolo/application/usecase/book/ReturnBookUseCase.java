package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.model.loan.valueobjects.LoanId;

public interface ReturnBookUseCase {

    void execute(LoanId loanId);

}