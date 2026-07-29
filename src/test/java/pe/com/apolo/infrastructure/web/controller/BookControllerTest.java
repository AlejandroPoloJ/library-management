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
import pe.com.apolo.application.usecase.book.*;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.infrastructure.config.SecurityConfig;
import pe.com.apolo.infrastructure.security.JwtAuthenticationFilter;
import pe.com.apolo.infrastructure.web.dto.request.CreateBookRequest;
import pe.com.apolo.infrastructure.web.dto.response.BookResponse;
import pe.com.apolo.infrastructure.web.mapper.BookRequestMapper;
import pe.com.apolo.infrastructure.web.mapper.BookResponseMapper;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CreateBookUseCase createBookUseCase;

    @MockitoBean
    private GetBookByIdUseCase getBookByIdUseCase;

    @MockitoBean
    private GetAllBooksUseCase getAllBooksUseCase;

    @MockitoBean
    private AddBookCopyUseCase addBookCopyUseCase;

    @MockitoBean
    private BookRequestMapper requestMapper;

    @MockitoBean
    private BookResponseMapper responseMapper;

    @BeforeEach
    void setUpJwtFilterBypass() throws Exception {
        doAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());
    }

    private CreateBookRequest buildCreateBookRequest() {
        return new CreateBookRequest(
                "Clean Code",
                "9780132350884",
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateBookWhenUserIsAdmin() throws Exception {
        CreateBookRequest request = buildCreateBookRequest();

        mockMvc.perform(post("/api/v1/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "MEMBER")
    void shouldForbidCreateBookWhenUserIsNotLibrarianOrAdmin() throws Exception {
        CreateBookRequest request = buildCreateBookRequest();

        mockMvc.perform(post("/api/v1/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void shouldReturn400WhenCreateBookRequestIsInvalid() throws Exception {
        CreateBookRequest invalidRequest = new CreateBookRequest(
                "", "9780132350884", "Robert C. Martin", 464, LocalDate.of(2008, Month.AUGUST, 1)
        );

        mockMvc.perform(post("/api/v1/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldGetBookById() throws Exception {
        UUID id = UUID.randomUUID();
        Book book = new Book(
                new BookId(id), "Clean Code", new ISBN("9780132350884"),
                "Robert C. Martin", 464, LocalDate.of(2008, Month.AUGUST, 1)
        );
        BookResponse response = new BookResponse(
                id, "Clean Code", "9780132350884", "Robert C. Martin",
                464, LocalDate.of(2008, Month.AUGUST, 1)
        );

        when(getBookByIdUseCase.execute(any())).thenReturn(book);
        when(responseMapper.toResponse(book)).thenReturn(response);

        mockMvc.perform(get("/api/v1/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @WithMockUser
    void shouldGetAllBooks() throws Exception {
        when(getAllBooksUseCase.execute()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void shouldAddBookCopy() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/books/{id}/copies", id))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "MEMBER")
    void shouldForbidAddBookCopyWhenUserIsNotLibrarianOrAdmin() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/books/{id}/copies", id))
                .andExpect(status().isForbidden());
    }
}