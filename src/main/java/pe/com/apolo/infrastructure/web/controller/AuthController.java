package pe.com.apolo.infrastructure.web.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.com.apolo.application.usecase.login.LoginUseCase;
import pe.com.apolo.infrastructure.web.dto.request.LoginRequest;
import pe.com.apolo.infrastructure.web.dto.response.LoginResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {

        String token = loginUseCase.execute(
                request.email(),
                request.password()
        );

        return new LoginResponse(token);
    }
}