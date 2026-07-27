package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.model.user.User;

public interface CreateUserUseCase {

    void execute(User user);

}