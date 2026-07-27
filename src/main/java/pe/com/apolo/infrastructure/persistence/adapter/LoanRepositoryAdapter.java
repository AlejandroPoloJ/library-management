package pe.com.apolo.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.LoanStatus;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.loan.LoanRepository;
import pe.com.apolo.infrastructure.persistence.mapper.LoanMapper;
import pe.com.apolo.infrastructure.persistence.repository.SpringDataLoanRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class LoanRepositoryAdapter implements LoanRepository {

    private final SpringDataLoanRepository repository;
    private final LoanMapper mapper;

    public LoanRepositoryAdapter(SpringDataLoanRepository repository, LoanMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Loan> findById(LoanId loanId) {
        return repository.findById(loanId.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<Loan> findByUserId(UserId userId) {
        return repository.findByUserId(userId.getValue())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Loan save(Loan loan) {
        return mapper.toDomain(
                repository.save(
                        mapper.toEntity(loan)
                )
        );
    }

    @Override
    public int countActiveLoansByUserId(UserId userId) {

        return (int) repository.countByUserIdAndStatus(
                userId.getValue(),
                LoanStatus.ACTIVE
        );
    }
}
