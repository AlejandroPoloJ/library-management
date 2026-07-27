package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

public interface GetUserByIdUseCase {

    User execute(UserId id);
}
