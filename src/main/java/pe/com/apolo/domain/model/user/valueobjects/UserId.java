package pe.com.apolo.domain.model.user.valueobjects;

import java.util.UUID;

public final class UserId {
    private final UUID value;

    public UserId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("UserId cannot be null.");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId userId)) return false;
        return value.equals(userId.value);
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
