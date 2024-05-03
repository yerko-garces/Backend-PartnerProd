package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.*;
import org.partnerprod.partnerprod.repositorio.EscenaRepositorio;
import org.partnerprod.partnerprod.repositorio.ItemRepositorio;
import org.partnerprod.partnerprod.repositorio.PersonajeRepositorio;
import org.partnerprod.partnerprod.repositorio.LocacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Escena guardarEscena(Escena escena) {
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

        Proyecto proyectoEscena = escena.getCapitulo().getProyecto();

        if (escena.getPersonajes() != null) {
            List<Personaje> personajes = escena.getPersonajes();
            for (int i = 0; i < personajes.size(); i++) {
                Personaje personaje = personajes.get(i);
                Personaje personajeExistente = personajeRepositorio.findById(personaje.getId()).orElse(null);
                if (personajeExistente != null) {
                    if (personajeExistente.getProyecto() == null) {
                        personajeExistente.setProyecto(proyectoEscena);
                        personajeRepositorio.save(personajeExistente);
                    }
                    personajes.set(i, personajeExistente);
                }
            }
        }

        if (escena.getLocacion() != null) {
            Locacion locacion = escena.getLocacion();
            Locacion locacionExistente = locacionRepositorio.findById(locacion.getId()).orElse(null);
            if (locacionExistente != null) {
                if (!locacionExistente.getProyecto().equals(proyectoEscena)) {
                    locacionExistente.setProyecto(proyectoEscena);
                    locacionRepositorio.save(locacionExistente);
                }
                escena.setLocacion(locacionExistente);
            }
        }

        return escenaRepositorio.save(escena);
    }

    public List<Escena> obtenerEscenasPorCapituloId(Long capituloId) {
        return escenaRepositorio.findAllByCapituloId(capituloId);
    }

    public Escena obtenerEscenaPorId(Long id) {
        return escenaRepositorio.findById(id).orElse(null);
    }

    public void eliminarEscena(Long id) {
        escenaRepositorio.deleteById(id);
    }
}