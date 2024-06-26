package org.partnerprod.partnerprod.controlador;

import com.google.gson.Gson;
import org.partnerprod.partnerprod.modelo.Personaje;
import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.servicio.PersonajeServicio;
import org.partnerprod.partnerprod.servicio.ProyectoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personajes")
@CrossOrigin(origins = "http://localhost:3000")
public class PersonajeControlador {
    @Autowired
    private PersonajeServicio personajeServicio;

    @Autowired
    private ProyectoServicio proyectoServicio;

    @PostMapping("/")
    public ResponseEntity<Personaje> crearPersonaje(@RequestBody Personaje personaje) {
        System.out.println("Entrando al método crearPersonaje");
        System.out.println("Personaje recibido: " + personaje);


        if (personaje.getProyecto() == null || personaje.getProyecto().getId() == null) {
            System.out.println("El id del proyecto no se ha proporcionado correctamente");
            return ResponseEntity.badRequest().body(null);
        }

        Long proyectoId = personaje.getProyecto().getId();
        System.out.println("Id del proyecto: " + proyectoId);

        Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);

        if (proyecto == null) {
            System.out.println("El proyecto no existe");
            return ResponseEntity.badRequest().body(null);
        }

        System.out.println("Proyecto obtenido: " + proyecto);

        personaje.setProyecto(proyecto);

        Personaje nuevoPersonaje = personajeServicio.guardarPersonaje(personaje);

        System.out.println("Personaje guardado: " + nuevoPersonaje);

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
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<Personaje>> obtenerPersonajesPorProyecto(@PathVariable Long proyectoId) {
        List<Personaje> personajes = personajeServicio.obtenerPersonajesPorProyecto(proyectoId);
        return ResponseEntity.ok(personajes.isEmpty() ? List.of() : personajes);
    }

    @GetMapping("/")
    public ResponseEntity<List<Personaje>> obtenerTodosLosPersonajes() {
        List<Personaje> personajes = personajeServicio.obtenerTodosLosPersonajes();
        return ResponseEntity.ok(personajes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personaje> actualizarPersonaje(@PathVariable Long id, @RequestBody Personaje personaje) {
        Personaje personajeExistente = personajeServicio.obtenerPersonajePorId(id);
        if (personajeExistente == null) {
            return ResponseEntity.notFound().build();
        }

        personaje.setId(id);

        if (personaje.getProyecto() == null) {
            personaje.setProyecto(personajeExistente.getProyecto());
        } else {
            Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(personaje.getProyecto().getId());
            if (proyecto == null) {
                return ResponseEntity.badRequest().body(null);
            }
            personaje.setProyecto(proyecto);
        }

        Personaje personajeActualizado = personajeServicio.guardarPersonaje(personaje);
        return ResponseEntity.ok(personajeActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonaje(@PathVariable Long id) {
        personajeServicio.eliminarPersonaje(id);
        return ResponseEntity.noContent().build();
    }
}
