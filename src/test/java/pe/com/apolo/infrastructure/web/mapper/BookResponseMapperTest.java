package pe.com.apolo.infrastructure.web.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.infrastructure.web.dto.response.BookResponse;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookResponseMapperTest {

    @Autowired
    private BookResponseMapper mapper;

    @Test
    void shouldMapBookToResponse() {

        UUID id = UUID.randomUUID();

        Book book = new Book(
                new BookId(id),
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );

        BookResponse response = mapper.toResponse(book);

        assertAll(
                () -> assertEquals(id, response.id()),
                () -> assertEquals("Clean Code", response.title()),
                () -> assertEquals("9780132350884", response.isbn()),
                () -> assertEquals("Robert C. Martin", response.author()),
                () -> assertEquals(464, response.pageCount()),
                () -> assertEquals(LocalDate.of(2008, Month.AUGUST, 1), response.publicationDate())
        );
    }
}