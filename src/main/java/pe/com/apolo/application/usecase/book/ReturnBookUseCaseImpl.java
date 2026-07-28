package pe.com.apolo.application.usecase.book;

import pe.com.apolo.domain.exception.LoanNotFoundException;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.repository.book.BookCopyRepository;
import pe.com.apolo.domain.repository.fine.FineRepository;
import pe.com.apolo.domain.repository.loan.LoanRepository;

public class ReturnBookUseCaseImpl implements ReturnBookUseCase {

    private final LoanRepository loanRepository;
    private final BookCopyRepository bookCopyRepository;
    private final FineRepository fineRepository;

    public ReturnBookUseCaseImpl(
            LoanRepository loanRepository,
            BookCopyRepository bookCopyRepository,
            FineRepository fineRepository
    ) {
        this.loanRepository = loanRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.fineRepository = fineRepository;
    }

    @Override
    public void execute(LoanId loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(LoanNotFoundException::new);

        loan.returnBook();

        // Persistir el cambio de estado del ejemplar
        bookCopyRepository.save(loan.getBookCopy());

        // Generar multa si corresponde
        loan.generateFine()
                .ifPresent(fineRepository::save);

        // Persistir el préstamo actualizado
        loanRepository.save(loan);
    }
}