package pe.com.apolo.domain.repository.fine;

import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

public interface FineRepository {

    Fine save(Fine fine);

    boolean existsPendingByUserId(UserId userId);

    Optional<Fine> findById(FineId fineId);

    List<Fine> findByUserId(UserId userId);

}