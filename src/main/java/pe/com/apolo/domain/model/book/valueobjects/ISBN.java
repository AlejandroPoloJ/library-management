package pe.com.apolo.domain.model.book.valueobjects;

public record ISBN(String value) {

    public ISBN {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        value = value.replace("-", "").trim();
        if (!value.matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("ISBN must have 10 or 13 digits");
        }
    }

    public String getValue() {
        return value;
    }
}