package pe.com.apolo.domain.exception;

public class InactiveUserException extends RuntimeException {

    public InactiveUserException() {
        super("User is inactive.");
    }
}
