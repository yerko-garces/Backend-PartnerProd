package org.partnerprod.partnerprod.servicio;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final Algorithm algorithm = Algorithm.HMAC256("partnerprod");

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("your-app")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .sign(algorithm);
    }

    public boolean validateToken(String token, String username) {
        String subject = JWT.require(algorithm)
                .withSubject(username)
                .build()
                .verify(token)
                .getSubject();
        return subject.equals(username);
    }
}

