package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Personaje;
import org.partnerprod.partnerprod.servicio.PersonajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeControlador {
    @Autowired
    private PersonajeServicio personajeServicio;

    @PostMapping("/")
    public ResponseEntity<Personaje> crearPersonaje(@RequestBody Personaje personaje) {
        Personaje nuevoPersonaje = personajeServicio.guardarPersonaje(personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPersonaje);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personaje> obtenerPersonajePorId(@PathVariable Long id) {
        Personaje personaje = personajeServicio.obtenerPersonajePorId(id);
        if (personaje != null) {
            return ResponseEntity.ok(personaje);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Personaje>> obtenerTodosLosPersonajes() {
        List<Personaje> personajes = personajeServicio.obtenerTodosLosPersonajes();
        return ResponseEntity.ok(personajes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personaje> actualizarPersonaje(@PathVariable Long id, @RequestBody Personaje personaje) {
        personaje.setId(id);
        Personaje personajeActualizado = personajeServicio.guardarPersonaje(personaje);
        return ResponseEntity.ok(personajeActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonaje(@PathVariable Long id) {
        personajeServicio.eliminarPersonaje(id);
        return ResponseEntity.noContent().build();
    }
}