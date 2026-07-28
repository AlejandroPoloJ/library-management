package pe.com.apolo.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.com.apolo.application.usecase.book.BorrowBookUseCase;
import pe.com.apolo.application.usecase.book.BorrowBookUseCaseImpl;
import pe.com.apolo.application.usecase.book.ReturnBookUseCase;
import pe.com.apolo.application.usecase.book.ReturnBookUseCaseImpl;
import pe.com.apolo.application.usecase.book.*;
import pe.com.apolo.application.usecase.fine.GetFinesByUserUseCase;
import pe.com.apolo.application.usecase.fine.GetFinesByUserUseCaseImpl;
import pe.com.apolo.application.usecase.fine.PayFineUseCase;
import pe.com.apolo.application.usecase.fine.PayFineUseCaseImpl;
import pe.com.apolo.application.usecase.loan.GetLoansByUserUseCase;
import pe.com.apolo.application.usecase.loan.GetLoansByUserUseCaseImpl;
import pe.com.apolo.application.usecase.loan.SimulateOverdueLoanUseCase;
import pe.com.apolo.application.usecase.loan.SimulateOverdueLoanUseCaseImpl;
import pe.com.apolo.application.usecase.login.LoginUseCase;
import pe.com.apolo.application.usecase.login.LoginUseCaseImpl;
import pe.com.apolo.application.usecase.user.*;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.domain.repository.book.BookRepository;
import pe.com.apolo.domain.repository.fine.FineRepository;
import pe.com.apolo.domain.repository.loan.LoanRepository;
import pe.com.apolo.domain.repository.user.UserRepository;
import pe.com.apolo.domain.service.JwtService;

@Configuration
public class UseCaseConfiguration {
    @Bean
    public BorrowBookUseCase borrowBookUseCase(
            UserRepository userRepository, BookRepository bookRepository,
            BookCopyRepository bookCopyRepository, LoanRepository loanRepository,
            FineRepository fineRepository
    ) {
        return new BorrowBookUseCaseImpl(
                userRepository, bookRepository, bookCopyRepository,
                loanRepository, fineRepository
        );
    }

    @Bean
    public ReturnBookUseCase returnBookUseCase(
            LoanRepository loanRepository, BookCopyRepository bookCopyRepository,
            FineRepository fineRepository
    ) {
        return new ReturnBookUseCaseImpl(
                loanRepository, bookCopyRepository, fineRepository
        );
    }

    @Bean
    public AddBookCopyUseCase addBookCopyUseCase(
            BookRepository bookRepository, BookCopyRepository bookCopyRepository
    ) {
        return new AddBookCopyUseCaseImpl(bookRepository, bookCopyRepository);
    }

    @Bean
    public CreateBookUseCase createBookUseCase(BookRepository bookRepository) {
        return new CreateBookUseCaseImpl(bookRepository);
    }

    @Bean
    public GetAllBooksUseCase getAllBooksUseCase(BookRepository bookRepository) {
        return new GetAllBooksUseCaseImpl(bookRepository);
    }

    @Bean
    public GetBookByIdUseCase getBookByIdUseCase(BookRepository bookRepository) {
        return new GetBookByIdUseCaseImpl(bookRepository);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        return new CreateUserUseCaseImpl(repository, passwordEncoder);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(
            UserRepository repository
    ) {
        return new GetUserByIdUseCaseImpl(repository);
    }

    @Bean
    public GetAllUsersUseCase getAllUsersUseCase(
            UserRepository repository
    ) {
        return new GetAllUsersUseCaseImpl(repository);
    }

    @Bean
    public GetLoansByUserUseCase getLoansByUserUseCase(
            LoanRepository loanRepository
    ) {
        return new GetLoansByUserUseCaseImpl(loanRepository);
    }

    @Bean
    public GetFinesByUserUseCase getFinesByUserUseCase(
            FineRepository fineRepository
    ){
        return new GetFinesByUserUseCaseImpl(fineRepository);
    }

    @Bean
    public PayFineUseCase payFineUseCase(FineRepository fineRepository) {
        return new PayFineUseCaseImpl(fineRepository);
    }

    @Bean
    public SimulateOverdueLoanUseCase simulateOverdueLoanUseCase(
            LoanRepository loanRepository
    ) {
        return new SimulateOverdueLoanUseCaseImpl(loanRepository);
    }

    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        return new LoginUseCaseImpl(
                userRepository,
                jwtService,
                authenticationManager
        );
    }

    @Bean
    public UpdateUserRoleUseCase updateUserRoleUseCase(
            UserRepository userRepository
    ) {
        return new UpdateUserRoleUseCaseImpl(userRepository);
    }
}
