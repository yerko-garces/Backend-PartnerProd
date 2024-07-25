package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.EscenaPosicion;
import org.partnerprod.partnerprod.modelo.Plan;
import org.partnerprod.partnerprod.modelo.PlanEscenaEtiqueta;
import org.partnerprod.partnerprod.servicio.EscenaServicio;
import org.partnerprod.partnerprod.servicio.PlanServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin(origins = "http://localhost:3000")
public class PlanController {
    @Autowired
    private PlanServicio planServicio;

    @Autowired
    private EscenaServicio escenaServicio;

    @PostMapping("/")
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan plan) {
        Plan nuevoPlan = planServicio.guardarPlan(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> obtenerPlanPorId(@PathVariable Long id) {
        Plan plan = planServicio.obtenerPlanPorId(id);
        if (plan != null) {
            return ResponseEntity.ok(plan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<Plan>> listarPlanesPorProyecto(@PathVariable Long proyectoId) {
        List<Plan> planes = planServicio.obtenerPlanesPorProyecto(proyectoId);
        return ResponseEntity.ok(planes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> actualizarPlan(@PathVariable Long id, @RequestBody Plan plan) {
        Plan planExistente = planServicio.obtenerPlanPorId(id);
        if (planExistente == null) {
            return ResponseEntity.notFound().build();
        }
        plan.setId(id);
        Plan planActualizado = planServicio.guardarPlan(plan);
        return ResponseEntity.ok(planActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable Long id) {
        try {
            planServicio.eliminarPlan(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{planId}/escena/{escenaId}")
    public ResponseEntity<?> agregarEscenaAPlan(
            @PathVariable Long planId,
            @PathVariable Long escenaId,
            @RequestParam(required = false) LocalTime hora,
            @RequestParam Integer posicion
    ) {
        escenaServicio.agregarEscenaAPlan(planId, escenaId, hora, posicion);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{planId}/etiqueta")
    public ResponseEntity<?> agregarEtiquetaAPlan(
            @PathVariable Long planId,
            @RequestParam String nombreEtiqueta,
            @RequestParam Integer posicion
    ) {
        escenaServicio.agregarEtiquetaAPlan(planId, nombreEtiqueta, posicion);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{planId}/escenas")
    public ResponseEntity<?> agregarEscenasAPlan(
            @PathVariable Long planId,
            @RequestBody List<EscenaPosicion> escenaPosiciones
    ) {
        escenaServicio.agregarEscenasAPlan(planId, escenaPosiciones);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{planId}/elementos")
    public ResponseEntity<?> actualizarElementosDePlan(
            @PathVariable Long planId,
            @RequestBody List<PlanEscenaEtiqueta> elementos
    ) {
        escenaServicio.actualizarElementosDePlan(planId, elementos);
        return ResponseEntity.ok().build();
    }
}