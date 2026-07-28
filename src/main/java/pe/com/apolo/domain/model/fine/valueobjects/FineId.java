package pe.com.apolo.domain.model.fine.valueobjects;

import java.util.UUID;

public record FineId(UUID value) {

    public FineId {
        if (value == null) {
            throw new IllegalArgumentException("FineId cannot be null.");
        }
    }

    public UUID getValue() {
        return value;
    }

    public static FineId generate() {
        return new FineId(UUID.randomUUID());
    }
}