package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Bloque;
import org.partnerprod.partnerprod.servicio.BloqueServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bloques")
@CrossOrigin
public class BloqueController {
    @Autowired
    private BloqueServicio bloqueServicio;

    @PostMapping("/{planDeRodajeId}")
    public ResponseEntity<Bloque> crearBloque(@PathVariable Long planDeRodajeId, @RequestBody Bloque bloque) {
        Bloque nuevoBloque = bloqueServicio.crearBloque(planDeRodajeId, bloque);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBloque(@PathVariable Long id) {
        bloqueServicio.eliminarBloque(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bloqueId}/escenas")
    public ResponseEntity<Void> asociarEscenaABloque(@PathVariable Long bloqueId, @RequestParam Long escenaId, @RequestParam LocalTime hora, @RequestParam Integer posicion) {
        bloqueServicio.asociarEscenaABloque(bloqueId, escenaId, hora, posicion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/escenas/{bloqueEscenaId}")
    public ResponseEntity<Void> actualizarEscenaEnBloque(@PathVariable Long bloqueEscenaId, @RequestParam LocalTime hora, @RequestParam Integer posicion) {
        bloqueServicio.actualizarEscenaEnBloque(bloqueEscenaId, hora, posicion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/escenas/{bloqueEscenaId}")
    public ResponseEntity<Void> eliminarEscenaDeBloque(@PathVariable Long bloqueEscenaId) {
        bloqueServicio.eliminarEscenaDeBloque(bloqueEscenaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bloqueId}/items")
    public ResponseEntity<Void> asociarItemABloque(@PathVariable Long bloqueId, @RequestParam Long itemId, @RequestParam int cantidad) {
        bloqueServicio.asociarItemABloque(bloqueId, itemId, cantidad);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/items/{bloqueItemId}")
    public ResponseEntity<Void> actualizarItemEnBloque(@PathVariable Long bloqueItemId, @RequestParam int cantidad) {
        bloqueServicio.actualizarItemEnBloque(bloqueItemId, cantidad);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{bloqueItemId}")
    public ResponseEntity<Void> eliminarItemDeBloque(@PathVariable Long bloqueItemId) {
        bloqueServicio.eliminarItemDeBloque(bloqueItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/actualizar")
    public ResponseEntity<List<Bloque>> actualizarBloques(@RequestBody List<Bloque> bloques) {
        List<Bloque> bloquesActualizados = new ArrayList<>();

        for (Bloque bloque : bloques) {
            if (bloque.getId() != null) {
                // Actualizar bloque existente
                Bloque bloqueActualizado = bloqueServicio.actualizarBloque(bloque.getId(), bloque);
                if (bloqueActualizado != null) {
                    bloquesActualizados.add(bloqueActualizado);
                }
            } else {
                // Crear nuevo bloque
                Long planDeRodajeId = bloque.getPlanDeRodaje().getId();
                Bloque nuevoBloque = bloqueServicio.crearBloque(planDeRodajeId, bloque);
                bloquesActualizados.add(nuevoBloque);
            }
        }

        return ResponseEntity.ok(bloquesActualizados);
    }
}