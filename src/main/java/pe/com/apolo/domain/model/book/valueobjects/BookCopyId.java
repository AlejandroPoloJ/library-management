package pe.com.apolo.domain.model.book.valueobjects;

import java.util.UUID;

public final class BookCopyId {
    private final UUID value;

    public BookCopyId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("BookCopyId cannot be null.");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    public static BookCopyId generate() {
        return new BookCopyId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopyId bookCopyId)) return false;
        return value.equals(bookCopyId.value);
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
