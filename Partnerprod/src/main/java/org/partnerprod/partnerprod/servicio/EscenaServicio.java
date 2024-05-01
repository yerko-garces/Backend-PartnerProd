package org.partnerprod.partnerprod.servicio;


import org.partnerprod.partnerprod.modelo.Escena;
import org.partnerprod.partnerprod.repositorio.EscenaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscenaServicio {
    @Autowired
    EscenaRepositorio escenaRepositorio;
    public Escena guardarEscena(Escena escena) { return escenaRepositorio.save(escena);}

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
