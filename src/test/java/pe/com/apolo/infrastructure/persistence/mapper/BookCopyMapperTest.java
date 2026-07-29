package pe.com.apolo.infrastructure.persistence.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.BookCopyStatus;
import pe.com.apolo.domain.model.book.valueobjects.BookCopyId;
import pe.com.apolo.infrastructure.persistence.entity.BookCopyEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookCopyMapperTest {

    @Autowired
    private BookCopyMapper mapper;

    @Test
    void shouldMapEntityToDomain() {

        UUID id = UUID.randomUUID();

        BookCopyEntity entity = new BookCopyEntity();
        entity.setId(id);
        entity.setStatus(BookCopyStatus.AVAILABLE);

        BookCopy copy = mapper.toDomain(entity);

        assertAll(
                () -> assertEquals(id, copy.getId().getValue()),
                () -> assertEquals(BookCopyStatus.AVAILABLE, copy.getStatus())
        );
    }

    @Test
    void shouldMapDomainToEntity() {

        UUID id = UUID.randomUUID();

        BookCopy copy = new BookCopy(
                new BookCopyId(id),
                BookCopyStatus.LOANED
        );

        BookCopyEntity entity = mapper.toEntity(copy);

        assertAll(
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals(BookCopyStatus.LOANED, entity.getStatus())
        );
    }
}