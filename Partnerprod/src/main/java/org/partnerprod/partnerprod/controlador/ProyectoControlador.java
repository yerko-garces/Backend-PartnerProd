package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.servicio.ProyectoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoControlador {

    @Autowired
    private ProyectoServicio proyectoServicio;

    @PostMapping("/")
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto) {
        Proyecto nuevoProyecto = proyectoServicio.guardarProyecto(proyecto);
        return ResponseEntity.ok(nuevoProyecto);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Proyecto>> listarProyectosPorUsuario(@PathVariable Long usuarioId) {
        List<Proyecto> proyectos = proyectoServicio.obtenerProyectosPorUsuarioId(usuarioId);
        return ResponseEntity.ok(proyectos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        proyecto.setId(id);
        Proyecto actualizado = proyectoServicio.guardarProyecto(proyecto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoServicio.eliminarProyecto(id);
        return ResponseEntity.ok().build();
    }
}

