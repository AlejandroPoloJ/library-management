package pe.com.apolo.domain.model.book.valueobjects;

import java.util.UUID;

public record BookId(UUID value) {

    public BookId {
        if (value == null) {
            throw new IllegalArgumentException("BookId cannot be null.");
        }
    }

    public UUID getValue() {
        return value;
    }

    public static BookId generate() {
        return new BookId(UUID.randomUUID());
    }
}