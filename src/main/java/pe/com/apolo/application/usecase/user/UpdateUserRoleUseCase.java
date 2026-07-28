package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

public interface UpdateUserRoleUseCase {

    void execute(UserId userId, Role role);

}