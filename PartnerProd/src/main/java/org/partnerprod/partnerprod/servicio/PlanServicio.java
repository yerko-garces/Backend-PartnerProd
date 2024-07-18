package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Escena;
import org.partnerprod.partnerprod.modelo.Plan;
import org.partnerprod.partnerprod.repositorio.EscenaRepositorio;
import org.partnerprod.partnerprod.repositorio.PlanRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void eliminarPlan(Long id) {
        planRepositorio.deleteById(id);
    }


}