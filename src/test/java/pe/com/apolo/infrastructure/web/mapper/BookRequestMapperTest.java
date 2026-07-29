package pe.com.apolo.infrastructure.web.mapper;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.infrastructure.web.dto.request.CreateBookRequest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class BookRequestMapperTest {

    private final BookRequestMapper mapper = new BookRequestMapper() {};

    @Test
    void shouldMapRequestToDomain() {
        CreateBookRequest request = new CreateBookRequest(
                "Clean Code", "9780132350884", "Robert C. Martin",
                464, LocalDate.of(2008, Month.AUGUST, 1)
        );

        Book book = mapper.toDomain(request);

        assertThat(book.getId()).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Clean Code");
        assertThat(book.getIsbn().value()).isEqualTo("9780132350884");
        assertThat(book.getAuthor()).isEqualTo("Robert C. Martin");
        assertThat(book.getPageCount()).isEqualTo(464);
        assertThat(book.getPublicationDate()).isEqualTo(LocalDate.of(2008, Month.AUGUST, 1));
    }
}