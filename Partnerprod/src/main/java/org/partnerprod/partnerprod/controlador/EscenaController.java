package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Escena;
import org.partnerprod.partnerprod.servicio.EscenaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escenas")
@CrossOrigin(origins = "http://localhost:3000")
public class EscenaController {
    @Autowired
    private EscenaServicio escenaServicio;

    @PostMapping("/")
    public ResponseEntity<Escena> crearEscena(@RequestBody Escena escena) {
        Escena nuevaEscena = escenaServicio.guardarEscena(escena);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEscena);
    }

    @GetMapping("/capitulo/{capituloId}")
    public ResponseEntity<List<Escena>> listarEscenasPorCapitulo(@PathVariable Long capituloId) {
        List<Escena> escenas = escenaServicio.obtenerEscenasPorCapituloId(capituloId);
        return ResponseEntity.ok(escenas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Escena> obtenerEscenaPorId(@PathVariable Long id) {
        Escena escena = escenaServicio.obtenerEscenaPorId(id);
        if (escena != null) {
            return ResponseEntity.ok(escena);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Escena> actualizarEscena(@PathVariable Long id, @RequestBody Escena escena) {
        Escena escenaExistente = escenaServicio.obtenerEscenaPorId(id);
        if (escenaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        escena.setId(id);
        Escena escenaActualizada = escenaServicio.guardarEscena(escena);
        return ResponseEntity.ok(escenaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEscena(@PathVariable Long id) {
        escenaServicio.eliminarEscena(id);
        return ResponseEntity.noContent().build();
    }
}