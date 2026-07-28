package pe.com.apolo.domain.model.fine.valueobjects;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        validateAmount(amount);
        validateCurrency(currency);
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }

    private static void validateCurrency(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null.");
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}