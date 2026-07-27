package pe.com.apolo.infrastructure.web.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(

        UUID id,
        String fullName,
        LocalDate birthDate,
        boolean active

) {
}