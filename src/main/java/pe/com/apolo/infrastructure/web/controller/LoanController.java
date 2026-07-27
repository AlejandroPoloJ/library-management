package pe.com.apolo.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.com.apolo.application.usecase.BorrowBookUseCase;
import pe.com.apolo.application.usecase.ReturnBookUseCase;
import pe.com.apolo.application.usecase.loan.GetLoansByUserUseCase;
import pe.com.apolo.application.usecase.loan.SimulateOverdueLoanUseCase;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.infrastructure.web.dto.request.BorrowBookRequest;
import pe.com.apolo.infrastructure.web.dto.response.LoanResponse;
import pe.com.apolo.infrastructure.web.mapper.LoanRequestMapper;
import pe.com.apolo.infrastructure.web.mapper.LoanResponseMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final BorrowBookUseCase borrowBookUseCase;
    private final ReturnBookUseCase returnBookUseCase;
    private final GetLoansByUserUseCase getLoansByUserUseCase;
    private final SimulateOverdueLoanUseCase simulateOverdueLoanUseCase;
    private final LoanRequestMapper requestMapper;
    private final LoanResponseMapper responseMapper;

    public LoanController(BorrowBookUseCase borrowBookUseCase,
                          ReturnBookUseCase returnBookUseCase,
                          GetLoansByUserUseCase getLoansByUserUseCase,
                          SimulateOverdueLoanUseCase simulateOverdueLoanUseCase,
                          LoanRequestMapper requestMapper, LoanResponseMapper responseMapper) {
        this.borrowBookUseCase = borrowBookUseCase;
        this.returnBookUseCase = returnBookUseCase;
        this.getLoansByUserUseCase = getLoansByUserUseCase;
        this.simulateOverdueLoanUseCase = simulateOverdueLoanUseCase;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse borrowBook(
            @Valid @RequestBody BorrowBookRequest request
    ) {

        Loan loan = borrowBookUseCase.execute(
                requestMapper.toUserId(request.userId()),
                requestMapper.toBookId(request.bookId())
        );

        return responseMapper.toResponse(loan);
    }

    @PostMapping("/{loanId}/return")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void returnBook(
            @PathVariable UUID loanId
    ) {

        returnBookUseCase.execute(
                requestMapper.toLoanId(loanId)
        );
    }

    @GetMapping("/users/{userId}")
    public List<LoanResponse> getLoansByUser(
            @PathVariable UUID userId
    ) {
        return getLoansByUserUseCase.execute(
                        requestMapper.toUserId(userId)
                )
                .stream()
                .map(responseMapper::toResponse)
                .toList();
    }

    @PostMapping("/{loanId}/simulate-overdue/{days}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void simulateOverdue(
            @PathVariable UUID loanId,
            @PathVariable int days
    ) {

        simulateOverdueLoanUseCase.execute(
                requestMapper.toLoanId(loanId),
                days
        );
    }
}
