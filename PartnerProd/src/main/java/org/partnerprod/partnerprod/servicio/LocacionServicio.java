package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Locacion;
import org.partnerprod.partnerprod.repositorio.LocacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocacionServicio {
    @Autowired
    private LocacionRepositorio locacionRepositorio;

    public Locacion guardarLocacion(Locacion locacion) {
        return locacionRepositorio.save(locacion);
    }

    public Locacion obtenerLocacionPorId(Long id) {
        return locacionRepositorio.findById(id).orElse(null);
    }

    public List<Locacion> obtenerTodasLasLocaciones() {
        return locacionRepositorio.findAll();
    }

    public void eliminarLocacion(Long id) {
        locacionRepositorio.deleteById(id);
    }
}
