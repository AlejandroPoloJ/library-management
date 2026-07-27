package pe.com.apolo.domain.exception;

public class UnderageUserException extends RuntimeException{
    public UnderageUserException() {
        super("User must be at least 18 years old.");
    }
}
