package pe.com.apolo.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.fine.FineStatus;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.fine.FineRepository;
import pe.com.apolo.infrastructure.persistence.mapper.BookMapper;
import pe.com.apolo.infrastructure.persistence.mapper.FineMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataFineRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FineRepositoryAdapter implements FineRepository {

    private final SpringDataFineRepository repository;
    private final FineMapper mapper;

    public FineRepositoryAdapter(SpringDataFineRepository repository, FineMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Fine save(Fine fine) {
        return mapper.toDomain(
                repository.save(
                        mapper.toEntity(fine)
                )
        );
    }

    @Override
    public boolean existsPendingByUserId(UserId userId) {

        return repository.existsByLoanUserIdAndStatus(
                userId.getValue(),
                FineStatus.PENDING
        );
    }

    @Override
    public Optional<Fine> findById(FineId fineId) {
        return repository.findById(fineId.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<Fine> findByUserId(UserId userId) {
        return repository.findByLoanUserId(userId.getValue())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
