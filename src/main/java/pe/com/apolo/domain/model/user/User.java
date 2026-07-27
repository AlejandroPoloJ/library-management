package pe.com.apolo.domain.model.user;

import pe.com.apolo.domain.exception.InactiveUserException;
import pe.com.apolo.domain.exception.MaxActiveLoansExceededException;
import pe.com.apolo.domain.exception.UnderageUserException;
import pe.com.apolo.domain.exception.UserHasPendingFinesException;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.time.LocalDate;
import java.time.Period;

public class User {

    private final UserId id;
    private String fullName;
    private LocalDate birthDate;
    private boolean active;

    public User(
            UserId id,
            String fullName,
            LocalDate birthDate,
            boolean active
    ) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.active = active;
    }

    public void validateCanBorrowBook(
            int activeLoans,
            boolean hasPendingFines
    ) {
        validateActive();
        validateAge();

        if (hasPendingFines) {
            throw new UserHasPendingFinesException();
        }

        if (activeLoans >= 5) {
            throw new MaxActiveLoansExceededException();
        }
    }

    private void validateActive() {
        if (!active) {
            throw new InactiveUserException();
        }
    }

    private void validateAge() {
        int years = Period.between(birthDate, LocalDate.now()).getYears();

        if (years < 18) {
            throw new UnderageUserException();
        }
    }

    public UserId getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isActive() {
        return active;
    }
}