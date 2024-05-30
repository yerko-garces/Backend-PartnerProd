package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.PlanDeRodaje;
import org.partnerprod.partnerprod.modelo.Usuario;
import org.partnerprod.partnerprod.servicio.PlanDeRodajeServicio;
import org.partnerprod.partnerprod.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planes-de-rodaje")
public class PlanDeRodajeController {
    @Autowired
    private PlanDeRodajeServicio planDeRodajeServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/{proyectoId}")
    public ResponseEntity<PlanDeRodaje> crearPlanDeRodaje(@PathVariable Long proyectoId, @RequestBody PlanDeRodaje planDeRodaje) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorNombre(nombreUsuario);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        planDeRodaje.setUsuario(usuario);
        PlanDeRodaje nuevoPlanDeRodaje = planDeRodajeServicio.crearPlanDeRodaje(proyectoId, planDeRodaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlanDeRodaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlanDeRodaje(@PathVariable Long id) {
        planDeRodajeServicio.eliminarPlanDeRodaje(id);
        return ResponseEntity.noContent().build();
    }
}