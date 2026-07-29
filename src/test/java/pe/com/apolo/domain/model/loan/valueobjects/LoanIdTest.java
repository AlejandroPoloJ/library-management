package pe.com.apolo.domain.model.loan.valueobjects;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class LoanIdTest {

    @Test
    void shouldCreateLoanIdSuccessfully() {
        UUID uuid = UUID.randomUUID();
        LoanId loanId = new LoanId(uuid);
        assertEquals(uuid, loanId.value());
        assertEquals(uuid, loanId.getValue());
    }

    @Test
    void shouldGenerateRandomLoanId() {
        assertNotNull(LoanId.generate());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new LoanId(null));
        assertEquals("LoanId cannot be null.", exception.getMessage());
    }
}