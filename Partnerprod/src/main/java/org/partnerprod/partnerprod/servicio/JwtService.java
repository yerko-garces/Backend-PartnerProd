package org.partnerprod.partnerprod.servicio;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

}
