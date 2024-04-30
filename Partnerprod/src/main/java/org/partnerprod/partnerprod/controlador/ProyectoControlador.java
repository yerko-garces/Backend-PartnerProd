package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.servicio.ProyectoServicio;
import org.partnerprod.partnerprod.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoControlador {

    @Autowired
    private ProyectoServicio proyectoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/")
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        proyecto.setUsuario(usuario);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        proyecto.setUsuario(usuario);
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
