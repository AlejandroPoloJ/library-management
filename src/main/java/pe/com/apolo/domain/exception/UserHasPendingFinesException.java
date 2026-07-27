package pe.com.apolo.domain.exception;

public class UserHasPendingFinesException extends RuntimeException{
    public UserHasPendingFinesException() {
        super("User has pending fines.");
    }
}
