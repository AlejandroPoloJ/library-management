package pe.com.apolo.application.usecase.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.apolo.domain.exception.BookNotAvailableException;
import pe.com.apolo.domain.exception.BookNotFoundException;
import pe.com.apolo.domain.exception.UserNotFoundException;
import pe.com.apolo.domain.model.book.Book;
import pe.com.apolo.domain.model.book.BookCopy;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.book.valueobjects.ISBN;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.domain.repository.book.BookRepository;
import pe.com.apolo.domain.repository.fine.FineRepository;
import pe.com.apolo.domain.repository.loan.LoanRepository;
import pe.com.apolo.domain.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowBookUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookCopyRepository bookCopyRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private FineRepository fineRepository;

    @InjectMocks
    private BorrowBookUseCaseImpl useCase;

    private User buildUser() {
        return new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.now().minusYears(25),
                true,
                Role.USER,
                "test@test.com",
                "123456"
        );
    }

    private Book buildBook(BookId id) {
        return new Book(
                id,
                "Clean Code",
                new ISBN("9780132350884"),
                "Robert C. Martin",
                464,
                LocalDate.of(2008, Month.AUGUST, 1)
        );
    }

    @Test
    void shouldBorrowBookSuccessfully() {

        User user = buildUser();
        BookId bookId = BookId.generate();

        Book book = buildBook(bookId);
        BookCopy copy = BookCopy.create();

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(loanRepository.countActiveLoansByUserId(user.getId()))
                .thenReturn(0);

        when(fineRepository.existsPendingByUserId(user.getId()))
                .thenReturn(false);

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));

        when(bookCopyRepository.findAvailableByBookId(bookId))
                .thenReturn(Optional.of(copy));

        when(bookCopyRepository.save(any(BookCopy.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(i -> i.getArgument(0));

        Loan loan = useCase.execute(user.getId(), bookId);

        assertNotNull(loan);
        assertEquals(user, loan.getUser());
        assertEquals(copy, loan.getBookCopy());

        verify(userRepository).findById(user.getId());
        verify(loanRepository).countActiveLoansByUserId(user.getId());
        verify(fineRepository).existsPendingByUserId(user.getId());
        verify(bookRepository).findById(bookId);
        verify(bookCopyRepository).findAvailableByBookId(bookId);
        verify(bookCopyRepository).save(copy);
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        UserId userId = UserId.generate();
        BookId bookId = BookId.generate();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> useCase.execute(userId, bookId)
        );
    }

    @Test
    void shouldThrowWhenBookDoesNotExist() {
        User user = buildUser();
        UserId userId = user.getId();
        BookId bookId = BookId.generate();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(loanRepository.countActiveLoansByUserId(userId))
                .thenReturn(0);
        when(fineRepository.existsPendingByUserId(userId))
                .thenReturn(false);
        when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> useCase.execute(userId, bookId)
        );
    }

    @Test
    void shouldThrowWhenBookIsNotAvailable() {
        User user = buildUser();
        UserId userId = user.getId();
        BookId bookId = BookId.generate();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(loanRepository.countActiveLoansByUserId(userId))
                .thenReturn(0);
        when(fineRepository.existsPendingByUserId(userId))
                .thenReturn(false);
        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(buildBook(bookId)));
        when(bookCopyRepository.findAvailableByBookId(bookId))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotAvailableException.class,
                () -> useCase.execute(userId, bookId)
        );
    }
}