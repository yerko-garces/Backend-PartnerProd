package org.partnerprod.partnerprod;
import static org.junit.jupiter.api.Assertions.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.partnerprod.partnerprod.servicio.JwtService;

import java.util.Date;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);

        assertNotNull(token);

        // Decodificar el token para verificar los detalles
        String decodedUsername = JWT.decode(token).getSubject();
        String issuer = JWT.decode(token).getIssuer();
        Date issuedAt = JWT.decode(token).getIssuedAt();
        Date expiresAt = JWT.decode(token).getExpiresAt();

        assertEquals(username, decodedUsername);
        assertEquals("your-app", issuer);
        assertNotNull(issuedAt);
        assertNotNull(expiresAt);

        // Verificar que la fecha de expiración sea aproximadamente 24 horas después de la fecha de emisión
        long expectedExpiry = issuedAt.getTime() + 86400000;
        long actualExpiry = expiresAt.getTime();
        assertTrue(Math.abs(expectedExpiry - actualExpiry) < 1000); // permitir un margen de error de 1 segundo
    }
}
