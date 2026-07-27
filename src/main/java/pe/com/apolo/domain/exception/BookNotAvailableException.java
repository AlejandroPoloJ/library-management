package pe.com.apolo.domain.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException() {
        super("Book copy is not available for loan.");
    }
}
