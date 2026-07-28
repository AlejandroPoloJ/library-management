package pe.com.apolo.domain.model.user.valueobjects;

import java.util.UUID;

public record UserId(UUID value) {

    public UserId {
        if (value == null) {
            throw new IllegalArgumentException("UserId cannot be null.");
        }
    }

    public UUID getValue() {
        return value;
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }
}