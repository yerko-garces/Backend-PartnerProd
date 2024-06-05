package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Bloque;
import org.partnerprod.partnerprod.modelo.PlanDeRodaje;
import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.repositorio.PlanDeRodajeRepositorio;
import org.partnerprod.partnerprod.repositorio.ProyectoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanDeRodajeServicio {
    @Autowired
    private PlanDeRodajeRepositorio planDeRodajeRepositorio;

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    public PlanDeRodaje crearPlanDeRodaje(Long proyectoId, PlanDeRodaje planDeRodaje) {
        Proyecto proyecto = proyectoRepositorio.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        planDeRodaje.setProyecto(proyecto);

        return planDeRodajeRepositorio.save(planDeRodaje);
    }

    public List<Bloque> obtenerBloquesPorPlanDeRodajeId(Long planDeRodajeId) {
        PlanDeRodaje planDeRodaje = planDeRodajeRepositorio.findById(planDeRodajeId)
                .orElseThrow(() -> new RuntimeException("Plan de rodaje no encontrado"));

        return planDeRodaje.getBloques();
    }

    public void eliminarPlanDeRodaje(Long id) {
        planDeRodajeRepositorio.deleteById(id);
    }
}