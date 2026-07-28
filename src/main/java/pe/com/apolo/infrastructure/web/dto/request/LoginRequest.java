package pe.com.apolo.infrastructure.web.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
