package pe.com.apolo.application.usecase.loan;

import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.List;

public interface GetLoansByUserUseCase {

    List<Loan> execute(UserId userId);

}
