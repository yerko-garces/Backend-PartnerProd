package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.PlanDeRodaje;
import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.repositorio.PlanDeRodajeRepositorio;
import org.partnerprod.partnerprod.repositorio.ProyectoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void eliminarPlanDeRodaje(Long id) {
        planDeRodajeRepositorio.deleteById(id);
    }
}