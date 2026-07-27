package pe.com.apolo.domain.exception;

public class BookAlreadyAvailableException extends RuntimeException {
    public BookAlreadyAvailableException() {
        super("Book copy is not loaned.");
    }
}
