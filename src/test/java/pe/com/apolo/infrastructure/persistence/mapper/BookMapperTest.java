package pe.com.apolo.infrastructure.persistence.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.infrastructure.persistence.entity.BookEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookMapperTest {

    @Autowired
    private BookMapper mapper;

    @Test
    void shouldMapEntityToDomain() {

        UUID id = UUID.randomUUID();

        BookEntity entity = new BookEntity(
                id,
                "Clean Code",
                "9780132350884",
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );

        Book book = mapper.toDomain(entity);

        assertAll(
                () -> assertEquals(id, book.getId().getValue()),
                () -> assertEquals("Clean Code", book.getTitle()),
                () -> assertEquals("9780132350884", book.getIsbn().getValue()),
                () -> assertEquals("Robert C. Martin", book.getAuthor()),
                () -> assertEquals(464, book.getPageCount()),
                () -> assertEquals(LocalDate.of(2008, Month.AUGUST, 1), book.getPublicationDate())
        );
    }

    @Test
    void shouldMapDomainToEntity() {

        UUID id = UUID.randomUUID();

        Book book = new Book(
                new BookId(id),
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );

        BookEntity entity = mapper.toEntity(book);

        assertAll(
                () -> assertEquals(id, entity.getId()),
                () -> assertEquals("Clean Code", entity.getTitle()),
                () -> assertEquals("9780132350884", entity.getIsbn()),
                () -> assertEquals("Robert C. Martin", entity.getAuthor()),
                () -> assertEquals(464, entity.getPageCount()),
                () -> assertEquals(LocalDate.of(2008, Month.AUGUST, 1), entity.getPublicationDate())
        );
    }
}