package pe.com.apolo.domain.model.loan.valueobjects;

import java.util.UUID;

public final class LoanId {
    private final UUID value;

    public LoanId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("LoanId cannot be null.");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    public static LoanId generate() {
        return new LoanId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanId loanId)) return false;
        return value.equals(loanId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
