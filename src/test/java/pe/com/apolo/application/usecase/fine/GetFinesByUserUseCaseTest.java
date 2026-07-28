package pe.com.apolo.application.usecase.fine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.fine.FineRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetFinesByUserUseCaseTest {

    @Mock
    private FineRepository fineRepository;

    @InjectMocks
    private GetFinesByUserUseCaseImpl useCase;

    @Test
    void shouldReturnFinesByUser() {

        UserId userId = UserId.generate();

        List<Fine> fines = List.of(
                mock(Fine.class),
                mock(Fine.class)
        );

        when(fineRepository.findByUserId(userId))
                .thenReturn(fines);

        List<Fine> result = useCase.execute(userId);

        assertEquals(fines, result);

        verify(fineRepository).findByUserId(userId);
        verifyNoMoreInteractions(fineRepository);
    }
}