package pe.com.apolo.application.usecase.fine;

import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.fine.FineRepository;

import java.util.List;

public class GetFinesByUserUseCaseImpl implements GetFinesByUserUseCase{

    private final FineRepository fineRepository;

    public GetFinesByUserUseCaseImpl(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    @Override
    public List<Fine> execute(UserId userId) {
        return fineRepository.findByUserId(userId);
    }
}
