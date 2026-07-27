package pe.com.apolo.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.com.apolo.application.usecase.fine.GetFinesByUserUseCase;
import pe.com.apolo.application.usecase.fine.PayFineUseCase;
import pe.com.apolo.infrastructure.web.dto.response.FineResponse;
import pe.com.apolo.infrastructure.web.mapper.FineRequestMapper;
import pe.com.apolo.infrastructure.web.mapper.FineResponseMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fines")
public class FineController {

    private final GetFinesByUserUseCase getFinesByUserUseCase;
    private final PayFineUseCase payFineUseCase;

    private final FineRequestMapper requestMapper;
    private final FineResponseMapper responseMapper;

    public FineController(
            GetFinesByUserUseCase getFinesByUserUseCase,
            PayFineUseCase payFineUseCase,
            FineRequestMapper requestMapper,
            FineResponseMapper responseMapper
    ) {
        this.getFinesByUserUseCase = getFinesByUserUseCase;
        this.payFineUseCase = payFineUseCase;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @GetMapping("/users/{userId}")
    public List<FineResponse> getByUser(
            @PathVariable UUID userId
    ) {

        return getFinesByUserUseCase.execute(
                        requestMapper.toUserId(userId)
                )
                .stream()
                .map(responseMapper::toResponse)
                .toList();
    }

    @PostMapping("/{fineId}/pay")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payFine(
            @PathVariable UUID fineId
    ) {

        payFineUseCase.execute(
                requestMapper.toFineId(fineId)
        );
    }
}
