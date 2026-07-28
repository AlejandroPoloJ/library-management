package pe.com.apolo.domain.model.book.valueobjects;

import java.util.UUID;

public record BookCopyId(UUID value) {

    public BookCopyId {
        if (value == null) {
            throw new IllegalArgumentException("BookCopyId cannot be null.");
        }
    }

    public UUID getValue() {
        return value;
    }

    public static BookCopyId generate() {
        return new BookCopyId(UUID.randomUUID());
    }
}
