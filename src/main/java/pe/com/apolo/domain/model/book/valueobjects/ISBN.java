package pe.com.apolo.domain.model.book.valueobjects;

import java.util.Objects;

public final class ISBN {

    private final String value;

    public ISBN(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        String normalized = value.replace("-", "").trim();
        if (!normalized.matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("ISBN must have 10 or 13 digits");
        }
        this.value = normalized;
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;
        ISBN isbn = (ISBN) o;
        return value.equals(isbn.value);
    }

    @Override
    public int hashCode() { return Objects.hash(value); }

    @Override
    public String toString() { return value; }
}