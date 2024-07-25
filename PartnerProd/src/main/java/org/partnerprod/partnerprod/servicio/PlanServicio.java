package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Escena;
import org.partnerprod.partnerprod.modelo.Plan;
import org.partnerprod.partnerprod.repositorio.EscenaRepositorio;
import org.partnerprod.partnerprod.repositorio.PlanRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServicio {
    @Autowired
    private PlanRepositorio planRepositorio;

    @Autowired
    private EscenaRepositorio escenaRepositorio;

    public Plan guardarPlan(Plan plan) {
        return planRepositorio.save(plan);
    }

    public Plan obtenerPlanPorId(Long id) {
        return planRepositorio.findById(id).orElse(null);
    }

    public List<Plan> obtenerTodosLosPlanes() {
        return planRepositorio.findAll();
    }

    @Transactional
    public void eliminarPlan(Long id) {
        Plan plan = planRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con id: " + id));

        // La eliminación en cascada se encargará de eliminar las PlanEscenaEtiqueta asociadas
        planRepositorio.delete(plan);
    }

    public List<Plan> obtenerPlanesPorProyecto(Long proyectoId) {
        return planRepositorio.findByProyectoId(proyectoId);
    }
}