package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.servicio.JwtService;
import org.partnerprod.partnerprod.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario login) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(login.getNombre());
        if (usuario != null && usuario.getContrasena().equals(login.getContrasena())) {
            String token = jwtService.generateToken(usuario.getNombre());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).build();
    }
}

