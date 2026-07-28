package pe.com.apolo.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.com.apolo.domain.model.user.Role;

import java.time.LocalDate;

public record CreateUserRequest(

        @NotBlank
        String fullName,

        @NotNull
        LocalDate birthDate,

        Role role,

        String email,

        String password
) {
}