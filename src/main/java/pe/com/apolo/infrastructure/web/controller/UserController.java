package pe.com.apolo.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.apolo.application.usecase.user.CreateUserUseCase;
import pe.com.apolo.application.usecase.user.GetAllUsersUseCase;
import pe.com.apolo.application.usecase.user.GetUserByIdUseCase;
import pe.com.apolo.application.usecase.user.UpdateUserRoleUseCase;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.web.dto.request.CreateUserRequest;
import pe.com.apolo.infrastructure.web.dto.request.UpdateUserRoleRequest;
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
    private final UpdateUserRoleUseCase updateUserRoleUseCase;
    private final UserRequestMapper requestMapper;
    private final UserResponseMapper responseMapper;

    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            GetAllUsersUseCase getAllUsersUseCase,
            UpdateUserRoleUseCase updateUserRoleUseCase,
            UserRequestMapper requestMapper,
            UserResponseMapper responseMapper
    ) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.updateUserRoleUseCase = updateUserRoleUseCase;
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

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRoleRequest request
    ) {

        updateUserRoleUseCase.execute(
                requestMapper.toUserId(id),
                request.role()
        );
    }

}