package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Personaje;
import org.partnerprod.partnerprod.repositorio.PersonajeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeServicio {
    @Autowired
    private PersonajeRepositorio personajeRepositorio;

    public Personaje guardarPersonaje(Personaje personaje) {
        return personajeRepositorio.save(personaje);
    }

    public Personaje obtenerPersonajePorId(Long id) {
        return personajeRepositorio.findById(id).orElse(null);
    }

    public List<Personaje> obtenerTodosLosPersonajes() {
        return personajeRepositorio.findAll();
    }

    public void eliminarPersonaje(Long id) {
        personajeRepositorio.deleteById(id);
    }
}