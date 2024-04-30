package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.servicio.BodegaVirtualServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bodega")
public class BodegaVirtualControlador {

    @Autowired
    private BodegaVirtualServicio bodegaVirtualServicio;

}
