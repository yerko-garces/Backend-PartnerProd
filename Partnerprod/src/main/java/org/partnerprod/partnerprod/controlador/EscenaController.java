package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Capitulo;
import org.partnerprod.partnerprod.modelo.Escena;
import org.partnerprod.partnerprod.servicio.CapituloServicio;
import org.partnerprod.partnerprod.servicio.EscenaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escenas")
public class EscenaController {
    @Autowired
    CapituloServicio capituloServicio;
    @Autowired
    EscenaServicio escenaServicio;
    @PostMapping("/{idCapitulo}")
    public ResponseEntity<Escena> crearEscena(@PathVariable Long idCapitulo, @RequestBody Escena escena) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Capitulo capitulo = capituloServicio.obtenerCapituloPorId(idCapitulo);
        if (capitulo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        escena.setCapitulo(capitulo);
        Escena nuevaEscena = escenaServicio.guardarEscena(escena);
        return ResponseEntity.ok(nuevaEscena);
    }


    @GetMapping("/{capituloId}")
    public ResponseEntity<List<Escena>> listarEscenasPorCapitulo(@PathVariable Long capituloId) {
        List<Escena> escenas = escenaServicio.obtenerEscenasPorCapituloId(capituloId);
        return ResponseEntity.ok(escenas);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Escena> actualizarEscena(@PathVariable Long id, @RequestBody Escena escena) {
        escena.setId(id);
        Capitulo capitulo = escenaServicio.obtenerEscenaPorId(id).getCapitulo();
        escena.setCapitulo(capitulo);
        Escena actualizado = escenaServicio.guardarEscena(escena);
        return ResponseEntity.ok(actualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEscena(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        escenaServicio.eliminarEscena(id);
        return ResponseEntity.ok().build();
    }
}
