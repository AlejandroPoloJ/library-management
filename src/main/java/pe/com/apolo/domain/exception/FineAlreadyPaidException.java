package pe.com.apolo.domain.exception;

public class FineAlreadyPaidException extends RuntimeException {
    public FineAlreadyPaidException() {
        super("Only pending fines can be paid.");
    }
}
