package pe.com.apolo.domain.model.fine;

import pe.com.apolo.domain.exception.FineAlreadyPaidException;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.model.fine.valueobjects.Money;
import pe.com.apolo.domain.model.loan.Loan;

import java.time.LocalDateTime;

public class Fine {
    private final FineId id;
    private final Money money;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private final Loan loan;
    private FineStatus status;

    public Fine(FineId id, Money money, Loan loan,
            LocalDateTime createdAt, LocalDateTime paidAt, FineStatus status
    ) {
        validateId(id);
        validateMoney(money);
        validateLoan(loan);

        this.id = id;
        this.money = money;
        this.loan = loan;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
        this.status = status;
    }

    private static void validateId(FineId id) {
        if (id == null) {
            throw new IllegalArgumentException("FineId cannot be null");
        }
    }

    private static void validateMoney(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("Money cannot be null.");
        }
    }

    private static void validateLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
    }

    public void pay() {
        if (status == FineStatus.PAID) {
            throw new FineAlreadyPaidException();
        }
        status = FineStatus.PAID;
        paidAt = LocalDateTime.now();
    }

    public void cancel(){
        if (status != FineStatus.PENDING) {
            throw new IllegalStateException("Only pending fines can be cancelled.");
        }
        status = FineStatus.CANCELLED;
    }

    public void forgive(){
        if (status != FineStatus.PENDING) {
            throw new IllegalStateException("Only pending fines can be forgiven.");
        }
        status = FineStatus.FORGIVEN;
    }

    public boolean isPending() {
        return status == FineStatus.PENDING;
    }

    public static Fine create(Money money, Loan loan) {
        return new Fine(
                FineId.generate(),
                money,
                loan,
                LocalDateTime.now(),
                null,
                FineStatus.PENDING
        );
    }

    public FineId getId() {
        return id;
    }

    public FineStatus getStatus() {
        return status;
    }

    public Money getMoney() {
        return money;
    }

    public Loan getLoan() {
        return loan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}
