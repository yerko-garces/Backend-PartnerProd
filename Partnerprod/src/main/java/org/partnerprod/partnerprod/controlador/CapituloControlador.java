package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.servicio.CapituloServicio;
import org.partnerprod.partnerprod.modelo.Capitulo;
import org.partnerprod.partnerprod.servicio.ProyectoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/capitulos")
@CrossOrigin(origins = "http://localhost:3000")
public class CapituloControlador {
    @Autowired
    CapituloServicio capituloServicio;
    @Autowired
    ProyectoServicio proyectoServicio;
    @PostMapping("/{idProyecto}")
    public ResponseEntity<Capitulo> crearCapitulo(@PathVariable Long idProyecto, @RequestBody Capitulo capitulo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(idProyecto);
        if (proyecto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        capitulo.setProyecto(proyecto);
        Capitulo nuevoCapitulo = capituloServicio.guardarCapitulo(capitulo);
        return ResponseEntity.ok(nuevoCapitulo);
    }
    @GetMapping("/{proyectoId}")
    public ResponseEntity<List<Capitulo>> listarCapitulosPorProyecto(@PathVariable Long proyectoId) {
        List<Capitulo> capitulos = capituloServicio.obtenerCapitulosPorProyectoId(proyectoId);
        return ResponseEntity.ok(capitulos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Capitulo> actualizarCapitulo(@PathVariable Long id, @RequestBody Capitulo capituloActualizado) {
        Capitulo capituloExistente = capituloServicio.obtenerCapituloPorId(id);
        if (capituloExistente == null) {
            return ResponseEntity.notFound().build();
        }

        capituloExistente.setNombre_capitulo(capituloActualizado.getNombre_capitulo());
        Capitulo capituloActualizadoResult = capituloServicio.guardarCapitulo(capituloExistente);

        return ResponseEntity.ok(capituloActualizadoResult);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCapitulo(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        capituloServicio.eliminarCapitulo(id);
        return ResponseEntity.ok().build();
    }
}
