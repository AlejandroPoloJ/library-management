package pe.com.apolo.application.usecase.fine;

import pe.com.apolo.domain.exception.FineNotFoundException;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.repository.fine.FineRepository;

public class PayFineUseCaseImpl implements PayFineUseCase {

    private final FineRepository fineRepository;

    public PayFineUseCaseImpl(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    @Override
    public void execute(FineId fineId) {

        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(FineNotFoundException::new);

        fine.pay();

        fineRepository.save(fine);
    }
}