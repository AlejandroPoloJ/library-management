package pe.com.apolo.domain.model.fine.valueobjects;

import java.util.UUID;

public final class FineId {
    private final UUID value;

    public FineId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("FineId cannot be null.");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    public static FineId generate() {
        return new FineId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FineId fineId)) return false;
        return value.equals(fineId.value);
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
