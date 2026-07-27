package pe.com.apolo.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.apolo.domain.model.fine.FineStatus;
import pe.com.apolo.infrastructure.persistence.entity.FineEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataFineRepository
        extends JpaRepository<FineEntity, UUID> {

    boolean existsByLoanUserIdAndStatus(
            UUID userId,
            FineStatus status
    );

    Optional<FineEntity> findById(UUID id);

    List<FineEntity> findByLoanUserId(UUID userId);

}