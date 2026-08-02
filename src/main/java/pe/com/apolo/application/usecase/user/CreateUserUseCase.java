package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.model.user.User;

public interface CreateUserUseCase {

    User execute(User user);

}