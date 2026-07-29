package pe.com.apolo.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import pe.com.apolo.domain.model.user.User;
import pe.com.apolo.domain.service.JwtService;
import pe.com.apolo.infrastructure.config.JwtProperties;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProperties properties;
    private final SecretKey key;

    public JwtServiceImpl(JwtProperties properties) {
        this.properties = properties;

        this.key = Keys.hmacShaKeyFor(
                properties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateToken(User user) {

        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(properties.expiration());

        long iatSeconds = now.getEpochSecond();
        long expSeconds = expiresAt.getEpochSecond();

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("iat", iatSeconds)
                .claim("exp", expSeconds)
                .signWith(key)
                .compact();
    }

    @Override
    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public boolean isValid(String token) {

        try {

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}