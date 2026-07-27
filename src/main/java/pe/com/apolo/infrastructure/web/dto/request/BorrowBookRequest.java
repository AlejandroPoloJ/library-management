package pe.com.apolo.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BorrowBookRequest(

        @NotNull
        UUID userId,

        @NotNull
        UUID bookId

) {
}