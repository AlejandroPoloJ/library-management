package pe.com.apolo.infrastructure.web.mapper;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FineRequestMapperTest {

    private final FineRequestMapper mapper = new FineRequestMapper();

    @Test
    void shouldMapToUserId() {
        UUID id = UUID.randomUUID();

        UserId result = mapper.toUserId(id);

        assertThat(result).isEqualTo(new UserId(id));
    }

    @Test
    void shouldMapToFineId() {
        UUID id = UUID.randomUUID();

        FineId result = mapper.toFineId(id);

        assertThat(result).isEqualTo(new FineId(id));
    }
}