package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Proyecto;
import org.partnerprod.partnerprod.repositorio.ProyectoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServicio {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepositorio.save(proyecto);
    }

    public Proyecto obtenerProyectoPorId(Long id) {
        return proyectoRepositorio.findById(id).orElse(null);
    }

    public List<Proyecto> obtenerProyectosPorUsuarioId(Long usuarioId) {
        return proyectoRepositorio.findByUsuarioId(usuarioId);
    }

    public void eliminarProyecto(Long id) {
        proyectoRepositorio.deleteById(id);
    }
}

