package pe.com.apolo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.apolo.domain.model.fine.Fine;
import pe.com.apolo.infrastructure.web.dto.response.FineResponse;

@Component
public class FineResponseMapper {

    public FineResponse toResponse(Fine fine) {
        return new FineResponse(
                fine.getId().getValue(),
                fine.getMoney().getAmount(),
                fine.getMoney().getCurrency().getCurrencyCode(),
                fine.getStatus(),
                fine.getCreatedAt(),
                fine.getPaidAt(),
                fine.getLoan().getId().getValue()
        );
    }
}