package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.servicio.JwtService;
import org.partnerprod.partnerprod.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario login) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(login.getNombre());
        if (usuario != null && passwordEncoder.matches(login.getContrasena(), usuario.getContrasena())) {
            String token = jwtService.generateToken(usuario.getNombre());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
