package pe.com.apolo.application.usecase.fine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.FineNotFoundException;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.repository.fine.FineRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayFineUseCaseTest {

    @Mock
    private FineRepository fineRepository;

    @InjectMocks
    private PayFineUseCaseImpl useCase;

    @Test
    void shouldPayFine() {

        Fine fine = mock(Fine.class);
        FineId fineId = FineId.generate();

        when(fineRepository.findById(fineId))
                .thenReturn(Optional.of(fine));

        useCase.execute(fineId);

        verify(fineRepository).findById(fineId);
        verify(fine).pay();
        verify(fineRepository).save(fine);
        verifyNoMoreInteractions(fineRepository, fine);
    }

    @Test
    void shouldThrowExceptionWhenFineDoesNotExist() {

        FineId fineId = FineId.generate();

        when(fineRepository.findById(fineId))
                .thenReturn(Optional.empty());

        assertThrows(
                FineNotFoundException.class,
                () -> useCase.execute(fineId)
        );

        verify(fineRepository).findById(fineId);
        verify(fineRepository, never()).save(any());
        verifyNoMoreInteractions(fineRepository);
    }
}