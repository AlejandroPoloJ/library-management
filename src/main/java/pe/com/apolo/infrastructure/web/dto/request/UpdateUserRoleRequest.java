package pe.com.apolo.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import pe.com.apolo.domain.model.user.Role;

public record UpdateUserRoleRequest(
        @NotNull
        Role role
) {
}
