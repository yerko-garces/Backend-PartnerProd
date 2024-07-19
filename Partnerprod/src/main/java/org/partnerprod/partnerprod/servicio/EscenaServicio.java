package org.partnerprod.partnerprod.servicio;

import org.hibernate.Hibernate;
import org.partnerprod.partnerprod.modelo.*;
import org.partnerprod.partnerprod.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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
    private PlanEscenaEtiquetaRepositorio planEscenaEtiquetaRepositorio;

    @Autowired
    private CapituloRepositorio capituloRepositorio;

    @Autowired
    private PlanRepositorio planRepositorio;

    @Autowired
    private EtiquetaRepositorio etiquetaRepositorio;

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

    public void agregarEscenaAPlan(Long planId, Long escenaId, LocalTime hora, Integer posicion) {
        Optional<Plan> planOptional = planRepositorio.findById(planId);
        Optional<Escena> escenaOptional = escenaRepositorio.findById(escenaId);

        if (planOptional.isPresent() && escenaOptional.isPresent()) {
            Plan plan = planOptional.get();
            Escena escena = escenaOptional.get();

            PlanEscenaEtiqueta planEscenaEtiqueta = new PlanEscenaEtiqueta();
            planEscenaEtiqueta.setPlan(plan);
            planEscenaEtiqueta.setEscena(escena);
            planEscenaEtiqueta.setHora(hora);
            planEscenaEtiqueta.setPosicion(posicion);

            planEscenaEtiquetaRepositorio.save(planEscenaEtiqueta);
        } else {
            // Manejar el caso donde el plan o la escena no existen (lanzar excepción, etc.)
        }
    }

    public void agregarEscenasAPlan(Long planId, List<EscenaPosicion> escenaPosiciones) {
        for (EscenaPosicion escenaPosicion : escenaPosiciones) {
            agregarEscenaAPlan(planId, escenaPosicion.getEscenaId(), escenaPosicion.getHora(), escenaPosicion.getPosicion());
        }
    }

    public void agregarEtiquetaAPlan(Long planId, String nombreEtiqueta, Integer posicion) {
        Optional<Plan> planOptional = planRepositorio.findById(planId);

        if (planOptional.isPresent()) {
            Plan plan = planOptional.get();

            Etiqueta etiqueta = etiquetaRepositorio.findByNombre(nombreEtiqueta);
            if (etiqueta == null) {
                etiqueta = new Etiqueta();
                etiqueta.setNombre(nombreEtiqueta);
                etiquetaRepositorio.save(etiqueta);
            }

            PlanEscenaEtiqueta planEscenaEtiqueta = new PlanEscenaEtiqueta();
            planEscenaEtiqueta.setPlan(plan);
            planEscenaEtiqueta.setEtiqueta(etiqueta);
            planEscenaEtiqueta.setPosicion(posicion);

            planEscenaEtiquetaRepositorio.save(planEscenaEtiqueta);
        } else {
            // Manejar el caso donde el plan no existe (lanzar excepción, etc.)
        }
    }

    public void actualizarElementosDePlan(Long planId, List<PlanEscenaEtiqueta> elementos) {
        Optional<Plan> planOptional = planRepositorio.findById(planId);

        if (planOptional.isPresent()) {
            Plan plan = planOptional.get();

            // Eliminar las relaciones existentes que no estén en la lista de elementos
            List<PlanEscenaEtiqueta> elementosExistentes = planEscenaEtiquetaRepositorio.findByPlanId(planId);
            for (PlanEscenaEtiqueta elemento : elementosExistentes) {
                if (!elementos.contains(elemento)) {
                    planEscenaEtiquetaRepositorio.delete(elemento);
                }
            }

            // Agregar o actualizar los elementos
            for (PlanEscenaEtiqueta elemento : elementos) {
                if (elemento.getEscena() != null) {
                    Escena escena = escenaRepositorio.findById(elemento.getEscena().getId()).orElse(null);
                    if (escena != null) {
                        elemento.setEscena(escena);
                    }
                } else if (elemento.getEtiqueta() != null) {
                    Etiqueta etiqueta = etiquetaRepositorio.findByNombre(elemento.getEtiqueta().getNombre());
                    if (etiqueta == null) {
                        etiqueta = new Etiqueta();
                        etiqueta.setNombre(elemento.getEtiqueta().getNombre());
                        etiquetaRepositorio.save(etiqueta);
                    }
                    elemento.setEtiqueta(etiqueta);
                }
                elemento.setPlan(plan);
                planEscenaEtiquetaRepositorio.save(elemento);
            }
        } else {
            // Manejar el caso donde el plan no existe (lanzar excepción, etc.)
        }
    }


}