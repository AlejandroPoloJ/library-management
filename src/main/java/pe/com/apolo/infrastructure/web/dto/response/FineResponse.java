package pe.com.apolo.infrastructure.web.dto.response;

import pe.com.apolo.domain.model.fine.FineStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FineResponse(
        UUID id,
        BigDecimal amount,
        String currency,
        FineStatus status,
        LocalDateTime createdAt,
        LocalDateTime paidAt,
        UUID loanId
) {
}