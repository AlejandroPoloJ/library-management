package pe.com.apolo.domain.model.book.valueobjects;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ISBNTest {

    @ParameterizedTest
    @ValueSource(strings = {"978-3-16-148410-0", "0306406152", "1234567890123", "1234567890"})
    void shouldCreateIsbnSuccessfullyAndFormatIt(String input) {
        ISBN isbn = new ISBN(input);
        String expected = input.replace("-", "").trim();
        assertEquals(expected, isbn.value());
        assertEquals(expected, isbn.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "}) // Prueba espacios en blanco para value.isBlank()
    void shouldThrowExceptionWhenIsbnIsEmpty(String invalidInput) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ISBN(invalidInput));
        assertEquals("ISBN cannot be empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "12345678901", "abcde12345", "12345678901234"}) // Ni 10 ni 13 dígitos numéricos
    void shouldThrowExceptionWhenIsbnLengthOrFormatIsInvalid(String invalidDigits) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ISBN(invalidDigits));
        assertEquals("ISBN must have 10 or 13 digits", exception.getMessage());
    }
}