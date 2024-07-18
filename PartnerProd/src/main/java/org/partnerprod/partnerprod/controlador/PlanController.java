package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Plan;
import org.partnerprod.partnerprod.servicio.EscenaServicio;
import org.partnerprod.partnerprod.servicio.PlanServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public ResponseEntity<List<Plan>> listarTodosLosPlanes() {
        List<Plan> planes = planServicio.obtenerTodosLosPlanes();
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
        planServicio.eliminarPlan(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{planId}/{escenaId}")
    public ResponseEntity<?> agregarEscenaAPlan(
            @PathVariable Long planId,
            @PathVariable Long escenaId
    ) {
        escenaServicio.agregarEscenaAPlan(planId, escenaId);
        return ResponseEntity.ok().build(); // Respuesta 200 OK
    }
}