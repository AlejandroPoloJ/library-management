package pe.com.apolo.infrastructure.web.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoanResponse(

        UUID loanId,

        UUID userId,

        UUID bookCopyId,

        LocalDateTime loanDate,

        LocalDateTime dueDate,

        String status

) {
}