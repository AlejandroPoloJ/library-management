package pe.com.apolo.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.apolo.application.usecase.user.CreateUserUseCase;
import pe.com.apolo.application.usecase.user.GetAllUsersUseCase;
import pe.com.apolo.application.usecase.user.GetUserByIdUseCase;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.web.dto.request.CreateUserRequest;
import pe.com.apolo.infrastructure.web.dto.response.UserResponse;
import pe.com.apolo.infrastructure.web.mapper.UserRequestMapper;
import pe.com.apolo.infrastructure.web.mapper.UserResponseMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;

    private final UserRequestMapper requestMapper;
    private final UserResponseMapper responseMapper;

    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            GetAllUsersUseCase getAllUsersUseCase,
            UserRequestMapper requestMapper,
            UserResponseMapper responseMapper
    ) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        createUserUseCase.execute(
                requestMapper.toDomain(request)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public UserResponse getById(
            @PathVariable UUID id
    ) {
        return responseMapper.toResponse(
                getUserByIdUseCase.execute(
                        new UserId(id)
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return getAllUsersUseCase.execute()
                .stream()
                .map(responseMapper::toResponse)
                .toList();
    }

}