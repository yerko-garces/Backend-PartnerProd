package org.partnerprod.partnerprod.servicio;

import org.hibernate.Hibernate;
import org.partnerprod.partnerprod.modelo.*;
import org.partnerprod.partnerprod.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EscenaServicio {
    @Autowired
    private EscenaRepositorio escenaRepositorio;

    @Autowired
    private PersonajeRepositorio personajeRepositorio;

    @Autowired
    private LocacionRepositorio locacionRepositorio;

    @Autowired
    private CapituloRepositorio capituloRepositorio;

    @Autowired
    private PlanRepositorio planRepositorio;

    public Escena guardarEscena(Escena escena) {
        Proyecto proyectoEscena = null;
        if (escena.getCapitulo() != null) {
            Capitulo capitulo = capituloRepositorio.findById(escena.getCapitulo().getId()).orElse(null);
            if (capitulo != null && capitulo.getProyecto() != null) {
                proyectoEscena = capitulo.getProyecto();
            }
        }
        System.out.println("Proyecto de la escena: " + proyectoEscena);

        List<Personaje> personajesActualizados = new ArrayList<>();
        if (escena.getPersonajes() != null && proyectoEscena != null) {
            List<Personaje> personajes = escena.getPersonajes();
            System.out.println("Personajes antes de la verificación: " + personajes);
            for (Personaje personaje : personajes) {
                Personaje personajeExistente = personajeRepositorio.findById(personaje.getId()).orElse(null);
                System.out.println("Personaje existente: " + personajeExistente);
                if (personajeExistente != null && personajeExistente.getProyecto().equals(proyectoEscena)) {
                    personajesActualizados.add(personajeExistente);
                }
            }
            System.out.println("Personajes después de la verificación: " + personajesActualizados);
        }
        escena.setPersonajes(personajesActualizados);

        Locacion locacionActualizada = null;
        if (escena.getLocacion() != null && proyectoEscena != null) {
            Locacion locacion = escena.getLocacion();
            System.out.println("Locación antes de la verificación: " + locacion);
            Locacion locacionExistente = locacionRepositorio.findById(locacion.getId()).orElse(null);
            System.out.println("Locación existente: " + locacionExistente);
            if (locacionExistente != null && locacionExistente.getProyecto().equals(proyectoEscena)) {
                locacionActualizada = locacionExistente;
            }
            System.out.println("Locación después de la verificación: " + locacionActualizada);
        }
        escena.setLocacion(locacionActualizada);

        return escenaRepositorio.save(escena);
    }

    public List<Escena> obtenerEscenasPorCapituloId(Long capituloId) {
        List<Escena> escenas = escenaRepositorio.findAllByCapituloId(capituloId);
        for (Escena escena : escenas) {
            Hibernate.initialize(escena.getPersonajes());
            Hibernate.initialize(escena.getLocacion());
        }
        return escenas;
    }


    public Escena obtenerEscenaPorId(Long id) {
        return escenaRepositorio.findById(id).orElse(null);
    }


    public void eliminarEscena(Long id) {
        escenaRepositorio.deleteById(id);
    }

    public List<Map<String, Object>> obtenerEscenasPorProyectoId(Long proyectoId) {
        List<Escena> escenas = escenaRepositorio.findByCapituloProyectoId(proyectoId);
        List<Map<String, Object>> escenasConCapitulo = new ArrayList<>();

        for (Escena escena : escenas) {
            Map<String, Object> escenaConCapitulo = new HashMap<>();
            escenaConCapitulo.put("escena", escena);
            escenaConCapitulo.put("capituloId", escena.getCapitulo().getId());
            escenasConCapitulo.add(escenaConCapitulo);
        }
        return escenasConCapitulo;
    }

    public void agregarEscenaAPlan(Long planId, Long escenaId) {
        Optional<Plan> planOptional = planRepositorio.findById(planId);
        Optional<Escena> escenaOptional = escenaRepositorio.findById(escenaId);

        if (planOptional.isPresent() && escenaOptional.isPresent()) {
            Plan plan = planOptional.get();
            Escena escena = escenaOptional.get();
            plan.getEscenas().add(escena);
            planRepositorio.save(plan);
        } else {
            // Manejar el caso donde el plan o la escena no existen (lanzar excepción, etc.)
        }
    }

    public void agregarEscenasAPlan(Long planId, List<Long> escenaIds) {
        for (Long escenaId : escenaIds) {
            agregarEscenaAPlan(planId, escenaId); // Llamada al método existente
        }
    }
}