package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.*;
import org.partnerprod.partnerprod.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EscenaServicio {
    @Autowired
    private EscenaRepositorio escenaRepositorio;

    @Autowired
    private ItemRepositorio itemRepositorio;

    @Autowired
    private PersonajeRepositorio personajeRepositorio;

    @Autowired
    private LocacionRepositorio locacionRepositorio;

    @Autowired
    private CapituloRepositorio capituloRepositorio;

    public Escena guardarEscena(Escena escena) {
        Escena escenaExistente = null;
        if (escena.getId() != null) {
            escenaExistente = escenaRepositorio.findById(escena.getId()).orElse(null);
        }

        if (escenaExistente != null && escenaExistente.getItems() != null) {
            List<Item> itemsEliminados = new ArrayList<>(escenaExistente.getItems());
            itemsEliminados.removeAll(escena.getItems());
            for (Item item : itemsEliminados) {
                Item itemExistente = itemRepositorio.findById(item.getId()).orElse(null);
                if (itemExistente != null) {
                    itemExistente.setCantidad(itemExistente.getCantidad() + 1);
                    itemRepositorio.save(itemExistente);
                }
            }
        }

        if (escena.getItems() != null) {
            List<Item> items = escena.getItems();
            for (Item item : items) {
                Item itemExistente = itemRepositorio.findById(item.getId()).orElse(null);
                if (itemExistente != null) {
                    itemExistente.setCantidad(itemExistente.getCantidad() - 1);
                    itemRepositorio.save(itemExistente);
                }
            }
        }

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
        return escenaRepositorio.findAllByCapituloId(capituloId);
    }

    public Escena obtenerEscenaPorId(Long id) {
        return escenaRepositorio.findById(id).orElse(null);
    }

    public void eliminarEscena(Long id) {
        Escena escenaExistente = escenaRepositorio.findById(id).orElse(null);
        if (escenaExistente != null) {
            if (escenaExistente.getItems() != null) {
                List<Item> items = escenaExistente.getItems();
                for (Item item : items) {
                    Item itemExistente = itemRepositorio.findById(item.getId()).orElse(null);
                    if (itemExistente != null) {
                        itemExistente.setCantidad(itemExistente.getCantidad() + 1);
                        itemRepositorio.save(itemExistente);
                    }
                }
            }
            escenaRepositorio.deleteById(id);
        }
    }
}