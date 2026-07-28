package pe.com.apolo.domain.model.loan.valueobjects;

import java.util.UUID;

public record LoanId(UUID value) {

    public LoanId {
        if (value == null) {
            throw new IllegalArgumentException("LoanId cannot be null.");
        }
    }

    public UUID getValue() {
        return value;
    }

    public static LoanId generate() {
        return new LoanId(UUID.randomUUID());
    }
}