package pe.com.apolo.domain.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException () {
        super("Book not found");
    }
}
