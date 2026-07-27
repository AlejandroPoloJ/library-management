package pe.com.apolo.domain.exception;

public class MaxActiveLoansExceededException extends RuntimeException{
    public MaxActiveLoansExceededException() {
        super("User has reached the maximum number of active loans.");
    }
}
