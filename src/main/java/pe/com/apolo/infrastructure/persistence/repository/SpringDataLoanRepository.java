package pe.com.apolo.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.apolo.domain.model.loan.LoanStatus;
import pe.com.apolo.infrastructure.persistence.entity.LoanEntity;

import java.util.List;
import java.util.UUID;

public interface SpringDataLoanRepository
        extends JpaRepository<LoanEntity, UUID> {

    long countByUserIdAndStatus(UUID userId, LoanStatus status);

    List<LoanEntity> findByUserId(UUID userId);
}