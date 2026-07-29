package pe.com.apolo.infrastructure.web.controller;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pe.com.apolo.application.usecase.fine.GetFinesByUserUseCase;
import pe.com.apolo.application.usecase.fine.PayFineUseCase;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.infrastructure.config.SecurityConfig;
import pe.com.apolo.infrastructure.security.JwtAuthenticationFilter;
import pe.com.apolo.infrastructure.web.mapper.FineRequestMapper;
import pe.com.apolo.infrastructure.web.mapper.FineResponseMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FineController.class)
@Import(SecurityConfig.class)
class FineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private GetFinesByUserUseCase getFinesByUserUseCase;

    @MockitoBean
    private PayFineUseCase payFineUseCase;

    @MockitoBean
    private FineRequestMapper requestMapper;

    @MockitoBean
    private FineResponseMapper responseMapper;

    @BeforeEach
    void setUpJwtFilterBypass() throws Exception {
        doAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());
    }

    @Test
    @WithMockUser
    void shouldGetFinesByUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserId mappedUserId = new UserId(userId);

        when(requestMapper.toUserId(userId)).thenReturn(mappedUserId);
        when(getFinesByUserUseCase.execute(mappedUserId)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/fines/users/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void shouldPayFine() throws Exception {
        UUID fineId = UUID.randomUUID();
        FineId mappedFineId = new FineId(fineId);

        when(requestMapper.toFineId(fineId)).thenReturn(mappedFineId);

        mockMvc.perform(post("/api/v1/fines/{fineId}/pay", fineId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "MEMBER")
    void shouldForbidPayFineWhenUserIsNotLibrarianOrAdmin() throws Exception {
        UUID fineId = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/fines/{fineId}/pay", fineId))
                .andExpect(status().isForbidden());
    }
}