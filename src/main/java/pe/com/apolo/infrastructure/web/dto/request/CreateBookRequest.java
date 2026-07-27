package pe.com.apolo.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateBookRequest(

        @NotBlank
        String title,

        @NotBlank
        String isbn,

        @NotBlank
        String author,

        @Min(1)
        int pageCount,

        @NotNull
        LocalDate publicationDate

) {
}