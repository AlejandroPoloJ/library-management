package pe.com.apolo.domain.service;

import pe.com.apolo.domain.model.user.User;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isValid(String token);

}
