package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.BodegaVirtual;
import org.partnerprod.partnerprod.repositorio.BodegaVirtualRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BodegaVirtualServicio {

    @Autowired
    private BodegaVirtualRepositorio bodegaVirtualRepositorio;

    public BodegaVirtual obtenerBodegaPorUsuarioId(Long usuarioId) {
        return bodegaVirtualRepositorio.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada para el usuario con ID: " + usuarioId));
    }

    public BodegaVirtual guardarBodega(BodegaVirtual bodegaVirtual) {
        return bodegaVirtualRepositorio.save(bodegaVirtual);
    }
}
