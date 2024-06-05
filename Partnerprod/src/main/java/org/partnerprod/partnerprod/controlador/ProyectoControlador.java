package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.PlanDeRodaje;
import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.servicio.ProyectoServicio;
import org.partnerprod.partnerprod.servicio.UsuarioServicio;
import org.partnerprod.partnerprod.servicio.PlanDeRodajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProyectoControlador {

    @Autowired
    private ProyectoServicio proyectoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PlanDeRodajeServicio planDeRodajeServicio;

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

        PlanDeRodaje nuevoPlanDeRodaje = new PlanDeRodaje();
        nuevoPlanDeRodaje.setProyecto(nuevoProyecto);
        nuevoPlanDeRodaje.setUsuario(usuario);
        planDeRodajeServicio.crearPlanDeRodaje(nuevoProyecto.getId(), nuevoPlanDeRodaje);

        return ResponseEntity.ok(nuevoProyecto);
    }
    @GetMapping("/usuario-id")
    public ResponseEntity<Long> obtenerUsuarioId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(usuario.getId());
    }


    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Proyecto>> listarProyectosPorUsuario(@PathVariable Long usuarioId) {
        List<Proyecto> proyectos = proyectoServicio.obtenerProyectosPorUsuarioId(usuarioId);
        return ResponseEntity.ok(proyectos);
    }
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<Proyecto> listarProyectos(@PathVariable Long proyectoId) {
        Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(proyectoId);
        return ResponseEntity.ok(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoActualizado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);

        Proyecto proyectoExistente = proyectoServicio.obtenerProyectoPorId(id);
        if (proyectoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        proyectoExistente.setTitulo(proyectoActualizado.getTitulo());
        Proyecto proyectoActualizadoResult = proyectoServicio.guardarProyecto(proyectoExistente);

        return ResponseEntity.ok(proyectoActualizadoResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoServicio.eliminarProyecto(id);
        return ResponseEntity.ok().build();
    }
}
