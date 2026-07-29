package pe.com.apolo.infrastructure.web.mapper;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoanRequestMapperTest {

    private final LoanRequestMapper mapper = new LoanRequestMapper();

    @Test
    void shouldMapToUserId() {
        UUID id = UUID.randomUUID();

        assertThat(mapper.toUserId(id)).isEqualTo(new UserId(id));
    }

    @Test
    void shouldMapToBookId() {
        UUID id = UUID.randomUUID();

        assertThat(mapper.toBookId(id)).isEqualTo(new BookId(id));
    }

    @Test
    void shouldMapToLoanId() {
        UUID id = UUID.randomUUID();

        assertThat(mapper.toLoanId(id)).isEqualTo(new LoanId(id));
    }
}