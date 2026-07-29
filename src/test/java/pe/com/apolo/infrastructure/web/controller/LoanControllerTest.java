package pe.com.apolo.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.apolo.application.usecase.book.BorrowBookUseCase;
import pe.com.apolo.application.usecase.book.ReturnBookUseCase;
import pe.com.apolo.application.usecase.loan.GetLoansByUserUseCase;
import pe.com.apolo.application.usecase.loan.SimulateOverdueLoanUseCase;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.config.SecurityConfig;
import pe.com.apolo.infrastructure.security.JwtAuthenticationFilter;
import pe.com.apolo.infrastructure.web.dto.request.BorrowBookRequest;
import pe.com.apolo.infrastructure.web.mapper.LoanRequestMapper;
import pe.com.apolo.infrastructure.web.mapper.LoanResponseMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
@Import(SecurityConfig.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private BorrowBookUseCase borrowBookUseCase;

    @MockitoBean
    private ReturnBookUseCase returnBookUseCase;

    @MockitoBean
    private GetLoansByUserUseCase getLoansByUserUseCase;

    @MockitoBean
    private SimulateOverdueLoanUseCase simulateOverdueLoanUseCase;

    @MockitoBean
    private LoanRequestMapper requestMapper;

    @MockitoBean
    private LoanResponseMapper responseMapper;

    @BeforeEach
    void setUpJwtFilterBypass() throws Exception {
        doAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void shouldBorrowBook() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        BorrowBookRequest request = new BorrowBookRequest(userId, bookId);

        when(requestMapper.toUserId(userId)).thenReturn(new UserId(userId));
        when(requestMapper.toBookId(bookId)).thenReturn(new BookId(bookId));
        when(borrowBookUseCase.execute(any(), any())).thenReturn(null);

        mockMvc.perform(post("/api/v1/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldReturn400WhenBorrowBookRequestIsInvalid() throws Exception {
        BorrowBookRequest invalidRequest = new BorrowBookRequest(null, null);

        mockMvc.perform(post("/api/v1/loans")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void shouldReturnBook() throws Exception {
        UUID loanId = UUID.randomUUID();
        when(requestMapper.toLoanId(loanId)).thenReturn(new LoanId(loanId));

        mockMvc.perform(post("/api/v1/loans/{loanId}/return", loanId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void shouldGetLoansByUser() throws Exception {
        UUID userId = UUID.randomUUID();
        when(requestMapper.toUserId(userId)).thenReturn(new UserId(userId));
        when(getLoansByUserUseCase.execute(any())).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/loans/users/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldSimulateOverdueLoan() throws Exception {
        UUID loanId = UUID.randomUUID();
        when(requestMapper.toLoanId(loanId)).thenReturn(new LoanId(loanId));

        mockMvc.perform(post("/api/v1/loans/{loanId}/simulate-overdue/{days}", loanId, 5))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void shouldForbidSimulateOverdueWhenUserIsNotAdmin() throws Exception {
        UUID loanId = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/loans/{loanId}/simulate-overdue/{days}", loanId, 5))
                .andExpect(status().isForbidden());
    }
}