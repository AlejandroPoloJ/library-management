package pe.com.apolo.application.usecase.fine;

import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.List;

public interface GetFinesByUserUseCase {

    List<Fine> execute(UserId userId);
}
