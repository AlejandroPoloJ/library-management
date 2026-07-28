package pe.com.apolo.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.apolo.application.usecase.book.AddBookCopyUseCase;
import pe.com.apolo.application.usecase.book.CreateBookUseCase;
import pe.com.apolo.application.usecase.book.GetAllBooksUseCase;
import pe.com.apolo.application.usecase.book.GetBookByIdUseCase;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.infrastructure.web.dto.request.CreateBookRequest;
import pe.com.apolo.infrastructure.web.dto.response.BookResponse;
import pe.com.apolo.infrastructure.web.mapper.BookRequestMapper;
import pe.com.apolo.infrastructure.web.mapper.BookResponseMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final CreateBookUseCase createBookUseCase;
    private final GetBookByIdUseCase getBookByIdUseCase;
    private final GetAllBooksUseCase getAllBooksUseCase;
    private final AddBookCopyUseCase addBookCopyUseCase;

    private final BookRequestMapper requestMapper;
    private final BookResponseMapper responseMapper;

    public BookController(
            CreateBookUseCase createBookUseCase,
            GetBookByIdUseCase getBookByIdUseCase,
            GetAllBooksUseCase getAllBooksUseCase,
            AddBookCopyUseCase addBookCopyUseCase,
            BookRequestMapper requestMapper,
            BookResponseMapper responseMapper
    ) {
        this.createBookUseCase = createBookUseCase;
        this.getBookByIdUseCase = getBookByIdUseCase;
        this.getAllBooksUseCase = getAllBooksUseCase;
        this.addBookCopyUseCase = addBookCopyUseCase;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@Valid @RequestBody CreateBookRequest request) {

        createBookUseCase.execute(
                requestMapper.toDomain(request)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public BookResponse getById(@PathVariable UUID id) {

        return responseMapper.toResponse(
                getBookByIdUseCase.execute(new BookId(id))
        );
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<BookResponse> getAll() {

        return getAllBooksUseCase.execute()
                .stream()
                .map(responseMapper::toResponse)
                .toList();
    }

    @PostMapping("/{id}/copies")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCopy(@PathVariable UUID id) {

        addBookCopyUseCase.execute(
                new BookId(id)
        );
    }

}
