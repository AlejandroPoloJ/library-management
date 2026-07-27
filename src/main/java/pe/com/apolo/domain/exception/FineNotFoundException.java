package pe.com.apolo.domain.exception;

public class FineNotFoundException extends RuntimeException {
    public FineNotFoundException() {
        super("Fine not found");
    }
}
