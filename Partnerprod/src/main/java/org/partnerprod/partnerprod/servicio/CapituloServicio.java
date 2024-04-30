package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Capitulo;

import org.partnerprod.partnerprod.repositorio.CapituloRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CapituloServicio {
    @Autowired
    CapituloRepositorio capituloRepositorio;
    public Capitulo guardarCapitulo(Capitulo capitulo) { return capituloRepositorio.save(capitulo);}

    public List<Capitulo> obtenerCapitulosPorProyectoId(Long proyectoId) {
        return capituloRepositorio.findAllByProyectoId(proyectoId);
    }
    public Optional<Capitulo> obtenerCapituloPorId(Long id) {
        return capituloRepositorio.findById(id);
    }

    public void eliminarCapitulo(Long id) {
        capituloRepositorio.deleteById(id);
    }
}
