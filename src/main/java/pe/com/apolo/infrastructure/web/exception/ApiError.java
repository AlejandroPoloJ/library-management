package pe.com.apolo.infrastructure.web.exception;

import java.time.LocalDateTime;

public record ApiError(

        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}
