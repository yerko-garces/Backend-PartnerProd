package org.partnerprod.partnerprod.controlador;

import org.partnerprod.partnerprod.modelo.Locacion;
import org.partnerprod.partnerprod.servicio.LocacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locaciones")
public class LocacionControlador {
    @Autowired
    private LocacionServicio locacionServicio;

    @PostMapping("/")
    public ResponseEntity<Locacion> crearLocacion(@RequestBody Locacion locacion) {
        Locacion nuevaLocacion = locacionServicio.guardarLocacion(locacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaLocacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacion> obtenerLocacionPorId(@PathVariable Long id) {
        Locacion locacion = locacionServicio.obtenerLocacionPorId(id);
        if (locacion != null) {
            return ResponseEntity.ok(locacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Locacion>> obtenerTodasLasLocaciones() {
        List<Locacion> locaciones = locacionServicio.obtenerTodasLasLocaciones();
        return ResponseEntity.ok(locaciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locacion> actualizarLocacion(@PathVariable Long id, @RequestBody Locacion locacion) {
        locacion.setId(id);
        Locacion locacionActualizada = locacionServicio.guardarLocacion(locacion);
        return ResponseEntity.ok(locacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLocacion(@PathVariable Long id) {
        locacionServicio.eliminarLocacion(id);
        return ResponseEntity.noContent().build();
    }
}