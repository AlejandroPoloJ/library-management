package pe.com.apolo.domain.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException() {
        super("Loan not found.");
    }
}
