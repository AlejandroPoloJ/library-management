package pe.com.apolo.application.usecase.user;

import pe.com.apolo.domain.model.user.User;

import java.util.List;

public interface GetAllUsersUseCase {

    List<User> execute();

}
