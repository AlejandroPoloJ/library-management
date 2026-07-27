package pe.com.apolo.application.usecase.fine;

import pe.com.apolo.domain.model.fine.valueobjects.FineId;

public interface PayFineUseCase {
    void execute(FineId fineId);
}
