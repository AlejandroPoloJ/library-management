package pe.com.apolo.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import pe.com.apolo.domain.model.user.Role;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void shouldReturnUserInformation() {

        User user = buildUser(true);

        CustomUserDetails details = new CustomUserDetails(user);

        assertAll(
                () -> assertEquals(user, details.getUser()),
                () -> assertEquals("admin@apolo.com", details.getUsername()),
                () -> assertEquals("123456", details.getPassword()),
                () -> assertTrue(details.isAccountNonExpired()),
                () -> assertTrue(details.isAccountNonLocked()),
                () -> assertTrue(details.isCredentialsNonExpired()),
                () -> assertTrue(details.isEnabled())
        );
    }

    @Test
    void shouldReturnRoleAuthority() {

        CustomUserDetails details =
                new CustomUserDetails(buildUser(true));

        Collection<? extends GrantedAuthority> authorities =
                details.getAuthorities();

        assertEquals(1, authorities.size());

        GrantedAuthority authority =
                authorities.iterator().next();

        assertEquals("ROLE_ADMIN", authority.getAuthority());
    }

    @Test
    void shouldReturnDisabledWhenUserIsInactive() {

        CustomUserDetails details =
                new CustomUserDetails(buildUser(false));

        assertFalse(details.isEnabled());
    }

    private User buildUser(boolean active) {

        return new User(
                UserId.generate(),
                "Alejandro",
                LocalDate.of(1998, Month.JANUARY, 15),
                active,
                Role.ADMIN,
                "admin@apolo.com",
                "123456"
        );
    }
}