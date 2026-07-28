package pe.com.apolo.domain.model.user;

import org.junit.jupiter.api.Test;
import pe.com.apolo.domain.exception.InactiveUserException;
import pe.com.apolo.domain.exception.MaxActiveLoansExceededException;
import pe.com.apolo.domain.exception.UnderageUserException;
import pe.com.apolo.domain.exception.UserHasPendingFinesException;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User buildUser(
            boolean active,
            LocalDate birthDate,
            Role role
    ) {
        return new User(
                UserId.generate(),
                "Alejandro",
                birthDate,
                active,
                role,
                "alejandro@test.com",
                "123456"
        );
    }

    @Test
    void shouldAllowBorrowWhenUserIsValid() {

        User user = buildUser(
                true,
                LocalDate.now().minusYears(25),
                Role.USER
        );
        assertDoesNotThrow(() ->
                user.validateCanBorrowBook(2, false)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserIsInactive() {

        User user = buildUser(
                false,
                LocalDate.now().minusYears(25),
                Role.USER
        );
        assertThrows(
                InactiveUserException.class,
                () -> user.validateCanBorrowBook(0, false)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserIsUnderage() {

        User user = buildUser(
                true,
                LocalDate.now().minusYears(17),
                Role.USER
        );
        assertThrows(
                UnderageUserException.class,
                () -> user.validateCanBorrowBook(0, false)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserHasPendingFines() {

        User user = buildUser(
                true,
                LocalDate.now().minusYears(30),
                Role.USER
        );
        assertThrows(
                UserHasPendingFinesException.class,
                () -> user.validateCanBorrowBook(0, true)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserHasFiveActiveLoans() {

        User user = buildUser(
                true,
                LocalDate.now().minusYears(30),
                Role.USER
        );
        assertThrows(
                MaxActiveLoansExceededException.class,
                () -> user.validateCanBorrowBook(5, false)
        );
    }

    @Test
    void shouldChangeRole() {

        User user = buildUser(
                true,
                LocalDate.now().minusYears(30),
                Role.USER
        );
        user.changeRole(Role.LIBRARIAN);
        assertEquals(Role.LIBRARIAN, user.getRole());
    }

    @Test
    void shouldThrowExceptionWhenRoleIsNull() {

        User user = buildUser(
                true,
                LocalDate.now().minusYears(30),
                Role.USER
        );
        assertThrows(IllegalArgumentException.class, () -> user.changeRole(null));
    }
}