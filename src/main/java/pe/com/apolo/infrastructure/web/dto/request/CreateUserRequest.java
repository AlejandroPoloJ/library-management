package pe.com.apolo.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateUserRequest(

        @NotBlank
        String fullName,

        @NotNull
        LocalDate birthDate,

        String email,

        String password
) {
}