package pe.com.apolo.domain.exception;

public class LoanAlreadyReturnedException extends RuntimeException{
    public LoanAlreadyReturnedException() {
        super("Loan is not active.");
    }
}
