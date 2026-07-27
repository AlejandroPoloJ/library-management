package pe.com.apolo.infrastructure.web.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record BookResponse(

        UUID id,
        String title,
        String isbn,
        String author,
        int pageCount,
        LocalDate publicationDate

) {
}