package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Bloque;
import org.partnerprod.partnerprod.servicio.BloqueServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloques")
@CrossOrigin
public class BloqueController {
    @Autowired
    private BloqueServicio bloqueServicio;

    @PostMapping("/")
    public ResponseEntity<Bloque> crearBloque(@RequestBody Bloque bloque) {
        Bloque nuevoBloque = bloqueServicio.crearBloque(bloque);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoBloque);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bloque> obtenerBloquePorId(@PathVariable Long id) {
        Bloque bloque = bloqueServicio.obtenerBloquePorId(id);
        if (bloque != null) {
            return ResponseEntity.ok(bloque);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bloque> actualizarBloque(@PathVariable Long id, @RequestBody Bloque bloque) {
        Bloque bloqueActualizado = bloqueServicio.actualizarBloque(id, bloque);
        if (bloqueActualizado != null) {
            return ResponseEntity.ok(bloqueActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<List<Bloque>> actualizarBloques(@RequestBody List<Bloque> bloques) {
        List<Bloque> bloquesActualizados = bloqueServicio.actualizarBloques(bloques);
        return ResponseEntity.ok(bloquesActualizados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBloque(@PathVariable Long id) {
        bloqueServicio.eliminarBloque(id);
        return ResponseEntity.noContent().build();
    }
}