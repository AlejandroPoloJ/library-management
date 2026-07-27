package pe.com.apolo.domain.model.book.valueobjects;

import java.util.UUID;

public final class BookId {

    private final UUID value;

    public BookId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("BookId cannot be null.");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    public static BookId generate() {
        return new BookId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookId bookId)) return false;
        return value.equals(bookId.value);
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